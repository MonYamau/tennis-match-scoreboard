package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TennisMatch {
    private static final int FINAL_SCORE = 2;

    private int firstPlayerScore;
    private int secondPlayerScore;
    private TennisSet currentSet;

    public static TennisMatch setupNewTennisMatch() {
        return new TennisMatch(0, 0, TennisSet.setupNewTennisSet());
    }

    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        Optional<PlayerNumber> setWinner = currentSet.recalculateScoreFor(player);
        if (setWinner.isEmpty()) {
            return Optional.empty();
        }
        incrementScoreFor(player);
        if (isWin(player)) {
            currentSet = TennisSet.setupNewTennisSet();
            return Optional.of(player);
        }
        currentSet = TennisSet.setupNewTennisSet();
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
