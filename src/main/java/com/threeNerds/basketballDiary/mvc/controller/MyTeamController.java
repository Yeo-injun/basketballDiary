package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ModifyMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.SearchMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.pagination.PaginatedMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.pagination.PaginatedTeamDTO;
import com.threeNerds.basketballDiary.mvc.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberService;
import com.threeNerds.basketballDiary.mvc.dto.pagination.PaginatedTeamMemeberDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.threeNerds.basketballDiary.constant.Constant.*;
import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;
import static com.threeNerds.basketballDiary.utils.SessionUtil.LOGIN_USER;

/**
 * 소속팀과 관련된 업무를 처리하는 Controller
 *
 * @author 책임자 작성
 * <p>
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.11 강창기 : 소속팀 목록조회 구현
 * 2022.02.15 여인준 : 소속팀 선수초대API 구현
 * 2022.02.23 강창기 : 소속팀 정보 수정 구현
 * 2022.02.24 강창기 : 소속팀 정보 삭제 구현
 * 2022.02.26 강창기 : 소속팀 운영진 조회 구현
 * 2022.02.27 강창기 : 소속팀 팀원 조회 구현
 * 2022.03.04 여인준 : 책임 API ResponseEntity적용
 * </pre>
 */


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myTeams")
public class MyTeamController {

    private final MyTeamService myTeamService;
    private final TeamMemberService teamMemberService;
    private final TeamMemberManagerService teamMemberManagerService;


    /**
     * API001 : 소속팀 운영진 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/managers")
    public ResponseEntity<List<MemberDTO>> searchManagers(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API001: MyTeamController.searchManagers");
        List<MemberDTO> managerList = myTeamService.findManagers(teamSeq);

        return ResponseEntity.ok().body(managerList);
    }

    /**
     * API002 : 소속팀 팀원목록 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/members")
    public ResponseEntity<PaginatedTeamMemeberDTO> searchMembers(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo
    ) {
        log.info("▒▒▒▒▒ API002: MyTeamController.searchMembers");
        PaginatedTeamMemeberDTO teamMembers = myTeamService.findMembers(teamSeq, pageNo);

        return ResponseEntity.ok().body(teamMembers);
    }

    /**
     * API003 : 소속팀 관리자임명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
    @PatchMapping("{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> appointManager (
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKey = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .teamMemberSeq(teamMemberSeq);

        teamMemberManagerService.appointManager(teamMemberKey);
        return RESPONSE_OK;
    }

    /**
     * API004 : 소속팀 회원 강퇴시키기
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
    @DeleteMapping("{teamSeq}/members/{teamMemberSeq}")
    public ResponseEntity<?> dischargeTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKey = new CmnMyTeamDTO()
                                .teamSeq(teamSeq)
                                .teamMemberSeq(teamMemberSeq);
        teamMemberManagerService.dischargeTeamMember(teamMemberKey);
        return RESPONSE_OK;
    }

    /**
     * API005 : 소속팀의 초대한 선수목록 조회
     * 22.03.22(화) 인준 : 공통DTO적용 및 동적쿼리 수정
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = MANAGER)
    @GetMapping("/{teamSeq}/joinRequestsTo")
    public ResponseEntity<?> searchInvitedPlayer(
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", required = false) String joinRequestStateCode
    ) {
        CmnMyTeamDTO playerSearchCond = new CmnMyTeamDTO()
                                            .teamSeq(teamSeq)
                                            .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchInvitedPlayer(playerSearchCond);
        return ResponseEntity.ok(playerList);
    }

    /**
     * API007 : 소속팀의 선수초대
     * 22.03.10 인준 : Service Layer에 CustomException 적용
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = MANAGER)
    @PostMapping("/{teamSeq}/joinRequestTo/{userSeq}")
    public ResponseEntity<?> inviteTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long userSeq
    ) {
        CmnMyTeamDTO joinRequest = new CmnMyTeamDTO()
                                            .teamSeq(teamSeq)
                                            .userSeq(userSeq);

        teamMemberManagerService.inviteTeamMember(joinRequest);
        return RESPONSE_OK;
    }

    /**
     * API008 : 소속팀이 받은 가입요청목록 조회
     * 22.03.23 인준 : QueryString 기본값 제거 및 필수값 설정 해제. 공통DTO로 Service넘겨주기
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = MANAGER)
    @GetMapping("/{teamSeq}/joinRequestsFrom")
    public ResponseEntity<?> searchJoinRequestPlayer (
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", required = false) String joinRequestStateCode
    ) {
        CmnMyTeamDTO playerSearchCond = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchJoinRequestPlayer(playerSearchCond);
        return ResponseEntity.ok(playerList);
    }

    /**
     * API009 : 소속팀이 사용자의 가입요청 승인
     * 22.03.10 인준 : Service Layer에 CustomException 적용
     * 22.03.25 인준 : CmnMyTeamDTO적용
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = MANAGER)
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long teamJoinRequestSeq,
            @PathVariable Long teamSeq
    ) {
        CmnMyTeamDTO joinRequest = new CmnMyTeamDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .teamSeq(teamSeq);

        teamMemberManagerService.approveJoinRequest(joinRequest);
        return RESPONSE_OK;
    }

    /**
     * API010 : 소속팀의 가입요청 거절
     * 22.03.25 인준 : CmnMyTeamDTO적용 및 예외처리
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = MANAGER)
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long teamSeq,
            @PathVariable Long teamJoinRequestSeq
    ) {
        CmnMyTeamDTO joinRequest = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .teamJoinRequestSeq(teamJoinRequestSeq);

        teamMemberManagerService.rejectJoinRequest(joinRequest);
        return RESPONSE_OK;
    }

    /**
     * API011 소속팀 개인프로필 수정데이터 조회
     */
    @GetMapping("/{teamSeq}/profile")
    public ResponseEntity<MemberDTO> findMyTeamsProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq
    ) {

        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        //ResponseMyTeamProfileDTO myTeamProfileDTO = teamMemberService.findProfile(findMyTeamProfileDTO);
        MemberDTO myTeamProfileDTO = teamMemberService.findProfile(findMyTeamProfileDTO);

        return ResponseEntity.ok(myTeamProfileDTO);
    }

    /**
     * API012 소속팀 개인프로필 수정
     */
    @PostMapping("/{teamSeq}/profile")
    public ResponseEntity<?> modifyMyTeamsProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq,
            @ModelAttribute MyTeamProfileDTO myTeamProfileDTO
    ) {

        uploadFile(myTeamProfileDTO.getImageFile());

        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        ModifyMyTeamProfileDTO teamProfileDTO = new ModifyMyTeamProfileDTO()
                .findMyTeamProfileDTO(findMyTeamProfileDTO)
                .backNumber(myTeamProfileDTO.getBackNumber());

        teamMemberService.updateMyTeamProfile(teamProfileDTO);
        return RESPONSE_OK;
    }

    public void uploadFile(MultipartFile file){
        /**
         * 파일을 저장할 경로를 미리 생성
         * ex) 오늘이 2022/06/18 이라면 D드라이브 upload 폴더에 2022->06->18 이라는 폴더들이 계층별로 생성됨
        */
        String uploadFolder = "D:\\upload";

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).replace("/", File.separator);

        File uploadPath = new File(uploadFolder,date);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        String uploadFileName = file.getOriginalFilename();
        File saveFile = new File(uploadPath,uploadFileName);

        try{
            file.transferTo(saveFile);
        }catch(IllegalStateException e){
            throw new RuntimeException(e.getMessage(),e);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    /**
     * API013 소속팀 탈퇴
     */
    @DeleteMapping("/{teamSeq}/profile")
    public ResponseEntity<?> deleteMyTeamsProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq)
    {
        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);
        teamMemberService.deleteMyTeamProfile(findMyTeamProfileDTO);
        return RESPONSE_OK;
    }

    /**
     * API014 : 소속팀 목록 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping
    public ResponseEntity<PaginatedMyTeamDTO> searchMyTeams(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestParam(name = "page-no", defaultValue = "0") Integer pageNo
    ) {
        log.info("▒▒▒▒▒ API014: MyTeamController.searchTeams");
        Long userSeq = SessionUtil.getUserSeq();

        SearchMyTeamDTO searchMyTeamDTO = new SearchMyTeamDTO()
                 .pageNo(pageNo)
                 .userSeq(userSeq);
        PaginatedMyTeamDTO myTeamList = myTeamService.findTeams(searchMyTeamDTO);

        return ResponseEntity.ok().body(myTeamList);
    }

    /**
     * API015 : 소속팀 관리자 제명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
    @DeleteMapping("/{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> dismissManager(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKeys = new CmnMyTeamDTO()
                .teamMemberSeq(teamMemberSeq)
                .teamSeq(teamSeq);
        teamMemberManagerService.dismissManager(teamMemberKeys);
        return RESPONSE_OK;
    }

    /**
     * API016 : 소속팀 정보 단건 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/info")
    public ResponseEntity<MyTeamDTO> searchTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API016: MyTeamController.searchTeam");
        Long userSeq = sessionUser.getUserSeq();
        FindMyTeamProfileDTO paramDTO = new FindMyTeamProfileDTO()
                .teamSeq(teamSeq)
                .userSeq(userSeq);
        MyTeamDTO myTeam = myTeamService.findTeam(paramDTO);

        return ResponseEntity.ok().body(myTeam);
    }

    /**
     * API017 : 소속팀 정보 수정
     */
    @Auth(GRADE = MANAGER)
    @PostMapping("/{teamSeq}/info")
    public ResponseEntity<?> modifyMyTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestBody MyTeamDTO dto
    ) {
        log.info("▒▒▒▒▒ API017: MyTeamController.modifyMyTeam");
        Long userSeq = sessionUser.getUserSeq();
        myTeamService.modifyMyTeam(teamSeq, dto);

        return RESPONSE_OK;
    }

    /**
     * API018 : 소속팀 정보 삭제
     */
    @Auth(GRADE = LEADER)
    @DeleteMapping("/{teamSeq}")
    public ResponseEntity<?> removeMyTeam(
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API018: MyTeamController.removeMyTeam");
        myTeamService.deleteMyTeam(teamSeq);

        // 삭제가 정상적으로 완료된 경우 204 No Content로 응답한다.
        return ResponseEntity.noContent().build();
    }

}
