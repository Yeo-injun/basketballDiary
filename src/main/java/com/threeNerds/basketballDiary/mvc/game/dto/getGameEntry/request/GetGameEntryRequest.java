package com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request;

import lombok.Getter;

@Getter
public class GetGameEntryRequest {

    private Long gameSeq;
    private String homeAwayCode;
    private String quarterCode;

    public GetGameEntryRequest ( Long gameSeq, String quarterCode, String homeAwayCode ) {
        this.gameSeq = gameSeq;
        this.quarterCode = quarterCode;
        this.homeAwayCode = homeAwayCode;
    }
}
