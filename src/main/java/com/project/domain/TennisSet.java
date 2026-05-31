package com.project.domain;

import java.util.Optional;

public class TennisSet {
    private static final int PREPONDERANCE = 2;
    private static final int MIN_VALUE_SCORE = 6;

    private int firstPlayerScore;
    private int secondPlayerScore;
    private TennisGame currentGame;

    public TennisSet() {
        this.firstPlayerScore = 0;
        this.secondPlayerScore = 0;
        this.currentGame = new TennisGame();
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public TennisGame getCurrentGame() {
        return currentGame;
    }

    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        Optional<PlayerNumber> gameWinner = currentGame.recalculateScoreFor(player);
        if (gameWinner.isEmpty()) {
            return Optional.empty();
        }
        incrementScoreFor(player);
        if (isWin(player)) {
            currentGame = new TennisGame();
            return Optional.of(player);
        }
        checkTieBreak();
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
        return firstPlayerScore >= MIN_VALUE_SCORE && (firstPlayerScore - secondPlayerScore >= PREPONDERANCE);
    }

    private boolean isWinOfSecondPlayer() {
        return secondPlayerScore >= MIN_VALUE_SCORE && (secondPlayerScore - firstPlayerScore >= PREPONDERANCE);
    }

    //ДОРАБОТАТЬ
    private void checkTieBreak() {
        if (firstPlayerScore == MIN_VALUE_SCORE && secondPlayerScore == MIN_VALUE_SCORE) {

        }
    }
}
