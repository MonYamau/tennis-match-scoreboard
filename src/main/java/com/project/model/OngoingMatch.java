package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.domain.TennisMatch;

import java.util.UUID;

public class OngoingMatch {
    @JsonIgnore
    private UUID id;
    private int firstPlayerId;
    private int secondPlayerId;
    private String firstPlayerName;
    private String secondPlayerName;
    private TennisMatch currentMatch;

    public OngoingMatch() {
    }

    public OngoingMatch(UUID id, int firstPlayerId, int secondPlayerId, String firstPlayerName,
                        String secondPlayerName, TennisMatch currentMatch) {
        this.id = id;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
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

    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(int firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public int getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(int secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }
}
