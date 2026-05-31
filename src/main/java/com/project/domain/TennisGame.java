package com.project.domain;

import java.util.Optional;

public class TennisGame {
    private static final int THIRTY_ORDINAL_NUMBER = Point.THIRTY.ordinal();
    private Point firstPlayerScore;
    private Point secondPlayerScore;

    public TennisGame() {
        this.firstPlayerScore = Point.ZERO;
        this.secondPlayerScore = Point.ZERO;
    }

    public Point getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public Point getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        if (isWin(player)) {
            return Optional.of(player);
        }
        incrementScoreFor(player);
        checkDeuce();
        return Optional.empty();
    }

    private void incrementScoreFor(PlayerNumber player) {
        if (player.equals(PlayerNumber.FIRST_PLAYER)) {
            firstPlayerScore = firstPlayerScore.next();
        } else {
            secondPlayerScore = secondPlayerScore.next();
        }
    }

    private boolean isWin(PlayerNumber playerNumber) {

        if (playerNumber.equals(PlayerNumber.FIRST_PLAYER)) {
            return isWinOfFirstPlayer();
        }
        return isWinOfSecondPlayer();
    }

    private boolean isWinOfFirstPlayer() {
        if (firstPlayerScore.equals(Point.FORTY) && (secondPlayerScore.ordinal() <= THIRTY_ORDINAL_NUMBER)) {
            return true;
        }
        return firstPlayerScore.equals(Point.ADVANTAGE) && secondPlayerScore.equals(Point.FORTY);
    }

    private boolean isWinOfSecondPlayer() {
        if (secondPlayerScore.equals(Point.FORTY) && (firstPlayerScore.ordinal() <= THIRTY_ORDINAL_NUMBER)) {
            return true;
        }
        return secondPlayerScore.equals(Point.ADVANTAGE) && firstPlayerScore.equals(Point.FORTY);
    }

    private void checkDeuce() {
        if (firstPlayerScore.equals(Point.ADVANTAGE) && secondPlayerScore.equals(Point.ADVANTAGE)) {
            firstPlayerScore = Point.FORTY;
            secondPlayerScore = Point.FORTY;
        }
    }
}
