package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.AuthUserRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    @Transactional
    public SessionUser login(CmnUserDTO cmnUserDTO)
    {
        log.info("Check User id = {}", cmnUserDTO.getUserId());
        User findLoginUser = userRepository.loginFindUser(cmnUserDTO);

        //null 인지 null이 아닌지 확신이 들지 않을때 사용 : ofNullable(null 이면 Optional.empty() 반환)
        SessionUser sessionUser = Optional.ofNullable(findLoginUser)
                                    .map(loginUser -> {
                                        List<AuthUserRequestDTO> userAuthList = userRepository.findAuthList(cmnUserDTO);

                                        /** 팀에 소속되어 있지 않을 경우 - 권한정보없이 SessionUser객체 생성 */
                                        // TODO 왜 list에 값이 없는데 size가 왜 1인지 확인 필요... >
                                        // Mybatis의 기본 설정이 조회 레코드가 없으면 null을 반환하게 됨.
                                        // 이때문에 List안에 첫번째 요소로 null이 들어가 있는 상태가 됨.
                                        // 추가적으로 레코드가 없을때 return되는 값을 null이 아닌 인스턴스로 할 수 있음
                                        // 하지만 이경우에도 조회된 값이 없기때문에 return되는 인스턴스 안에 필드는 모두 null이 됨.
                                        if (userAuthList.get(0) == null)
                                        {
                                            return new SessionUser(loginUser.getUserSeq(), loginUser.getUserId());
                                        }

                                        /** 권한정보 생성후 SesstionUser객체 return */
                                        Map<Long, Long> userAuth = userAuthList.stream()
                                                .collect(Collectors.toMap(authDTO -> Long.parseLong(authDTO.getTeamSeq()),
                                                                          authDTO -> Long.parseLong(authDTO.getTeamAuthCode())));
                                        return new SessionUser(loginUser.getUserSeq(), loginUser.getUserId(), userAuth);
                                    })
                                    // TODO 로그인 정보가 없는 경우(findLoginUser가 null일때) 에러를 던져줘야 함. (에러내용을 더 세분화해서 던져줘야 함.)
                                    // TODO 경우1 : ID가 없을 경우, 경우2 : ID가 있는데 비밀번호가 틀린경우 (현재는 임시로 처리)
                                    .orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND));

        return sessionUser;
    }

}
