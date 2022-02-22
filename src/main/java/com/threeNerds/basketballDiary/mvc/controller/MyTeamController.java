package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.session.SessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;

/**
 * ... 수행하는 Controller
 *
 * @author 책임자 작성
 * <p>
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.11 강창기 : 소속팀 목록조회 구현
 * 2022.02.15 여인준 : 소속팀 선수초대API 구현
 * </pre>
 */


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myTeams")
public class MyTeamController {

    private final MyTeamService myTeamService;
    private final TeamMemberManagerService teamMemberManagerService;

    /**
     * API005 : 소속팀의 초대한 선수목록 조회
     */
    @GetMapping("/{teamSeq}/joinRequestTo") // TODO URL 마지막에 /users 추가하는 것은 어떤지 (22.02.20 인준의견)
    public List<PlayerDTO> searchInvitedPlayer(
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", defaultValue = "00") String joinRequestStateCode
    ) {
        PlayerSearchDTO searchCond = new PlayerSearchDTO()
                                            .teamSeq(teamSeq)
                                            .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchInvitedPlayer(searchCond);
        return playerList;
    }


    /**
     * API007 : 소속팀의 선수초대
     */
    @PostMapping("/{teamSeq}/joinRequestTo/{userSeq}")
    public String inviteTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long userSeq
    ) {
        log.info("---INFO Controller.inviteTeamMember 진입 ---");
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                                            .teamSeq(teamSeq)
                                            .userSeq(userSeq);

        teamMemberManagerService.inviteTeamMember(joinRequest);
        return "OK"; // TODO 임시로 return값 반영
    }

    /**
     * API009 : 소속팀의 가입요청 승인
     */
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/approval")
    public String approveJoinRequest(
            @PathVariable Long teamSeq,
            @PathVariable Long teamJoinRequestSeq
    ) {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                .teamSeq(teamSeq)
                .teamJoinRequestSeq(teamJoinRequestSeq);

        teamMemberManagerService.approveJoinRequest(joinRequest);
        return "API009";
    }

    /**
     * API010 : 소속팀의 가입요청 거절
     */
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/rejection")
    public String rejectJoinRequest(
            @PathVariable Long teamSeq,
            @PathVariable Long teamJoinRequestSeq
    ) {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                .teamSeq(teamSeq)
                .teamJoinRequestSeq(teamJoinRequestSeq);

        teamMemberManagerService.rejectJoinRequest(joinRequest);
        
        return "";
    }

    /**
     * API014 : 소속팀 목록 조회
     */
    @GetMapping
    public List<MyTeamDTO> searchTeams(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO) {
        Long userSeq = sessionDTO.getUserSeq();

        List<MyTeamDTO> myTeamList = myTeamService.findTeams(userSeq);

        return myTeamList;
    }

    /**
     * API016 : 소속팀 정보 수정데이터 단건 조회
     */
    @GetMapping("/{teamSeq}/info")
    public MyTeamDTO searchTeam(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO, @PathVariable(value = "teamSeq") Long teamSeq) {
        Long userSeq = sessionDTO.getUserSeq();

        MyTeamDTO myTeam = myTeamService.findTeam(userSeq, teamSeq);

        return myTeam;
    }

    /**
     * API017 : 소속팀 정보 수정
     */
    @PutMapping("/{teamSeq}/info")
    public void modifyMyTeam(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO, @PathVariable(value = "teamSeq") Long teamSeq, @RequestBody MyTeamDTO dto) {
        Long userSeq = sessionDTO.getUserSeq();

        if(userSeq == null)
            throw new IllegalStateException("세션 정보가 존재하지 않습니다.");

        myTeamService.modifyMyTeam(teamSeq, dto);
    }
}
