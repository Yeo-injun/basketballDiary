package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 소속팀에서 팀원관리 및 소속팀정보 관리 등의 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;

    /**
     * 팀 목록 조회
     * @return List<TeamDTO>
     */
    public List<TeamDTO> searchTeams(SearchTeamDTO searchTeamDTO) {
        log.info("TeamService.searchTeams");
        List<TeamDTO> resultTeamList = new ArrayList<TeamDTO>();
        List<TeamInfoDTO> teamInfoList = teamRepository.findPagingTeam(searchTeamDTO);
        if(teamInfoList.isEmpty())
            teamInfoList = Collections.emptyList();

        teamInfoList.forEach(teamInfo -> {
            Long teamSeq = teamInfo.getTeamSeq();
            List<TeamRegularExercise> exerciseList = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
            TeamDTO teamDTO = new TeamDTO()
                    .teamInfoDTO(teamInfo)
                    .teamRegularExercisesList(exerciseList.isEmpty() ? Collections.emptyList() : exerciseList);

            resultTeamList.add(teamDTO);
        });

        return resultTeamList.isEmpty() ?
                Collections.emptyList() : resultTeamList;
    }
}
