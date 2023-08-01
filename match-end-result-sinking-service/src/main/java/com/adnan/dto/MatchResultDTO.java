package com.adnan.dto;

public class MatchResultDTO {
    public String matchId;
    public String matchName;
    public String endResult;

    MatchResultDTO(){}

    public MatchResultDTO(String matchId, String matchName, String endResult) {
        this.matchId = matchId;
        this.matchName = matchName;
        this.endResult = endResult;
    }
}
