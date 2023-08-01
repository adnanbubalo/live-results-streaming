package com.adnan.model;

import jakarta.persistence.*;

@Entity
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 36)
    private String matchId;
    @Column(length = 128)
    private String matchName;
    @Column(length = 128)
    private String endResult;

    public MatchResult(){}
    public MatchResult(Integer id, String matchId, String matchName, String endResult) {
        this.id = id;
        this.matchId = matchId;
        this.matchName = matchName;
        this.endResult = endResult;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public void setEndResult(String endResult) {
        this.endResult = endResult;
    }

    public Integer getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public String getMatchName() {
        return matchName;
    }

    public String getEndResult() {
        return endResult;
    }
}
