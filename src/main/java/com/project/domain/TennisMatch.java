package com.project.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TennisMatch {
    private static final int FINAL_SCORE = 2;

    private int firstPlayerScore;
    private int secondPlayerScore;
    private TennisSet currentSet;

    public TennisMatch() {
        this.firstPlayerScore = 0;
        this.secondPlayerScore = 0;
        this.currentSet = new TennisSet();
    }

    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        Optional<PlayerNumber> setWinner = currentSet.recalculateScoreFor(player);
        if (setWinner.isEmpty()) {
            return Optional.empty();
        }
        incrementScoreFor(player);
        if (isWin(player)) {
            currentSet = new TennisSet();
            return Optional.of(player);
        }
        currentSet = new TennisSet();
        return Optional.empty();
    }

    private void incrementScoreFor(PlayerNumber playerNumber) {
        if (playerNumber.equals(PlayerNumber.FIRST_PLAYER)) {
            firstPlayerScore++;
        } else {
            secondPlayerScore++;
        }
    }

    private boolean isWin(PlayerNumber playerNumber) {
        if (playerNumber.equals(PlayerNumber.FIRST_PLAYER)) {
            return isWinOfFirstPlayer();
        }
        return isWinOfSecondPlayer();
    }

    private boolean isWinOfFirstPlayer() {
        return firstPlayerScore == FINAL_SCORE;
    }

    private boolean isWinOfSecondPlayer() {
        return secondPlayerScore == FINAL_SCORE;
    }
}
