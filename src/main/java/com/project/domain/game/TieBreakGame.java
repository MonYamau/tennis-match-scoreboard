package com.project.domain.game;

import com.project.domain.PlayerNumber;

import java.util.Optional;

public class TieBreakGame implements GameMode {
    private static final int PREPONDERANCE = 2;
    private static final int MIN_VALUE_SCORE = 7;

    private int firstPlayerScore;
    private int secondPlayerScore;

    public TieBreakGame() {
        this.firstPlayerScore = 0;
        this.secondPlayerScore = 0;
    }

    public TieBreakGame(int firstPlayerScore, int secondPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    @Override
    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        incrementScoreFor(player);
        if (isWin(player)) {
            return Optional.of(player);
        }
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
        return firstPlayerScore >= MIN_VALUE_SCORE && firstPlayerScore - secondPlayerScore >= PREPONDERANCE;
    }

    private boolean isWinOfSecondPlayer() {
        return secondPlayerScore >= MIN_VALUE_SCORE && secondPlayerScore - firstPlayerScore >= PREPONDERANCE;
    }
}
