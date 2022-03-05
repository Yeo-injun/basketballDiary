package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.ResponseMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberService;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;
import static com.threeNerds.basketballDiary.utils.HttpResponses.*;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    /**
     * API029 회원가입
     */
    @PostMapping("/registration")
    public String createUser(@RequestBody @Valid UserDTO userDTO){

        User user = User.builder()
                .userId(userDTO.getUserId())
                .password(userDTO.getPassword())
                .userName(userDTO.getUserName())
                .positionCode(userDTO.getPositionCode())
                .email(userDTO.getEmail())
                .gender(userDTO.getGender())
                .birthYmd(userDTO.getBirthYmd())
                .height(userDTO.getHeight())
                .weight(userDTO.getWeight())
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode(userDTO.getSidoCode())
                .sigunguCode(userDTO.getSigunguCode())
                .build();

        userService.createMember(user);
        return "createOk";
    }

    @Auth(GRADE = 3L)
    @GetMapping("/testAnnotation/{teamId}")
    public void test(){
        log.info("Auth : 1");
    }

    /****************************************************************************************************************/

    /**
     *  API020 : 농구팀 가입요청 보내기
     **/
    // TODO 클래스단위의 url 매핑정보 수정에 따라 root url 수정 필요
    // TODO 로그인 여부 체크하는 동작 필요 - checkLogin 어노테이션 적용 요망
    @PostMapping("/{userSeq}/joinRequestTo/{teamSeq}")
    public ResponseEntity<?> sendJoinRequestToTeam(
            @PathVariable("userSeq") Long userSeq,
            @PathVariable("teamSeq") Long teamSeq
    )
    {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                                            .teamSeq(teamSeq)
                                            .userSeq(userSeq);

        userTeamManagerService.sendJoinRequestToTeam(joinRequest);
        return RESPONSE_CREATED;
    }

    /**
     *  API022 : 농구팀 가입요청 및 초대 목록 조회
     **/
    @GetMapping("/{userSeq}/joinRequestsAll")
    public ResponseEntity<?> searchJoinRequestsAll(
            @PathVariable Long userSeq
    )
    {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(userSeq);

        List<JoinRequestDTO> result = userTeamManagerService.searchJoinRequestsAll(joinRequestDTO);
        return ResponseEntity.ok(result);
    }
}
