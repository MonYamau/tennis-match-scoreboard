package com.project.domain;

import java.util.Optional;

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

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    public void setCurrentSet(TennisSet currentSet) {
        this.currentSet = currentSet;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public TennisSet getCurrentSet() {
        return currentSet;
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
