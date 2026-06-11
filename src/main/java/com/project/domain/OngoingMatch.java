package com.project.domain;

import java.util.Optional;
import java.util.UUID;

public class OngoingMatch {
    private final UUID uuid;
    private final int firstPlayerId;
    private final int secondPlayerId;
    private final String firstPlayerName;
    private final String secondPlayerName;
    private final TennisMatch currentMatch;
    private Integer winnerId;

    public OngoingMatch(UUID uuid, int firstPlayerId, int secondPlayerId, String firstPlayerName, String secondPlayerName, Integer winnerId, TennisMatch currentMatch) {
        this.uuid = uuid;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.winnerId = winnerId;
        this.currentMatch = currentMatch;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    public int getSecondPlayerId() {
        return secondPlayerId;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public TennisMatch getCurrentMatch() {
        return currentMatch;
    }

    public void recalculateScoreForMatch(int playerId) {
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
