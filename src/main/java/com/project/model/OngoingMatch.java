package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.domain.PlayerNumber;
import com.project.domain.TennisMatch;

import java.util.Optional;
import java.util.UUID;

public class OngoingMatch {
    @JsonIgnore
    private UUID uuid;
    private int firstPlayerId;
    private int secondPlayerId;
    private String firstPlayerName;
    private String secondPlayerName;
    private Integer winnerId;
    private TennisMatch currentMatch;

    public OngoingMatch() {
    }

    public OngoingMatch(UUID uuid, int firstPlayerId, int secondPlayerId, String firstPlayerName,
                        String secondPlayerName, TennisMatch currentMatch) {
        this.uuid = uuid;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.currentMatch = currentMatch;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public TennisMatch getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(TennisMatch currentMatch) {
        this.currentMatch = currentMatch;
    }

    public void recalculateScoreForMatch (int playerId) {
        if (playerId == firstPlayerId) {
            Optional<PlayerNumber> player = currentMatch.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
            if (player.isPresent()) {
                winnerId = firstPlayerId;
            }
        } else if (playerId == secondPlayerId) {
            Optional<PlayerNumber> player = currentMatch.recalculateScoreFor(PlayerNumber.SECOND_PLAYER);
            if (player.isPresent()) {
                winnerId = secondPlayerId;
            }
        }
    }
}
