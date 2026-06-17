package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OngoingMatch {
    private final UUID uuid;
    private final int firstPlayerId;
    private final int secondPlayerId;
    private final String firstPlayerName;
    private final String secondPlayerName;
    private final TennisMatch currentMatch;
    private Integer winnerId;

    public void recalculateScoreFor(int playerId) {
        checkMatchState();
        if (playerId == firstPlayerId) {
            recalculateScoreForFirstPlayer();
        } else if (playerId == secondPlayerId) {
            recalculateScoreForSecondPlayer();
        } else {
            throw new IllegalArgumentException("invalid value for player identification");
        }
    }

    private void checkMatchState() {
        if (winnerId != null) {
            throw new IllegalStateException("the current match is over");
        }
    }

    private void recalculateScoreForFirstPlayer() {
        Optional<PlayerNumber> matchWinner = currentMatch.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        if (matchWinner.isPresent()) {
            winnerId = firstPlayerId;
        }
    }

    private void recalculateScoreForSecondPlayer() {
        Optional<PlayerNumber> matchWinner = currentMatch.recalculateScoreFor(PlayerNumber.SECOND_PLAYER);
        if (matchWinner.isPresent()) {
            winnerId = secondPlayerId;
        }
    }
}
