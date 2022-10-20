package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /* PK : id */
    private Long userSeq;
    /* 아이디 */
    private String userId;
    /* 패스워드 */
    private String password;
    /* 이름 */
    private String userName; // TODO name으로 통일 - DB컬럼명과 일치해야 함.
    private String name;
    /* 포지션 코드 */
    private String positionCode;
    /* 이메일 */
    private String email;
    /* 성별 */
    private String gender;
    /* 생년월일 */
    private String birthYmd;
    /* 키 */
    private Double height;
    /* 몸무게 */
    private Double weight;
    /* 등록일자 */
    private LocalDate regDate;
    /* 업데이트 일자 */
    private LocalDate updateDate;
    /* 회원/비회원 */
    private String userRegYn;
    /* 시도코드 */
    private String sidoCode;
    /* 시군구코드 */
    private String sigunguCode;
    /* 도로명주소 */
    private String roadAddress;

    public static User createForRegistration(CmnUserDTO userDTO)
    {
        LocalDate today = LocalDate.now();
        return User.builder()
                .userId(userDTO.getUserId())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .gender(userDTO.getGender())
                .birthYmd(userDTO.getBirthYmd())
                .height(userDTO.getHeight())
                .weight(userDTO.getWeight())
                .regDate(today)
                .updateDate(today)
                .userRegYn("Y")
                .sidoCode(userDTO.getSidoCode())
                .sigunguCode(userDTO.getSigunguCode())
                .positionCode(userDTO.getPositionCode())
                .roadAddress(userDTO.getRoadAddress())
                .build();
    }

    // 로그인 상태 확인
    public boolean isLogin() {
        if (this.userSeq == null) {
            return false;
        }
        return true;
    }

    public boolean isMatchedPassword (String password)
    {
        if (password == null) {
            throw new CustomException(Error.NO_EXIST_PASSWORD);
        }

        // TODO 비밀번호 암호화 모듈 추가하기
        if (!this.password.equals(password)) {
            throw new CustomException(Error.INCORRECT_PASSWORD);
        }
        return true;
    }

}
