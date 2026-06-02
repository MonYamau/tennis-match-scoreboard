package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.domain.TennisMatch;

import java.util.UUID;

public class OngoingMatch {
    @JsonIgnore
    private UUID id;
    private String firstPlayerName;
    private String secondPlayerName;
    private TennisMatch currentMatch;

    public OngoingMatch() {
    }

    public OngoingMatch(UUID id, String firstPlayerName, String secondPlayerName, TennisMatch currentMatch) {
        this.id = id;
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.currentMatch = currentMatch;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public TennisMatch getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(TennisMatch currentMatch) {
        this.currentMatch = currentMatch;
    }
}
