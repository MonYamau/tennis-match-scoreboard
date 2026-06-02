package com.project.domain.game;

import com.project.domain.PlayerNumber;
import com.project.domain.Point;

import java.util.Optional;

public class DefaultGame implements GameMode {
    private static final int THIRTY_ORDINAL_NUMBER = Point.THIRTY.ordinal();

    private Point firstPlayerScore;
    private Point secondPlayerScore;

    public DefaultGame() {
        this.firstPlayerScore = Point.ZERO;
        this.secondPlayerScore = Point.ZERO;
    }

    public DefaultGame(Point firstPlayerScore, Point secondPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    public Point getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public void setFirstPlayerScore(Point firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public Point getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public void setSecondPlayerScore(Point secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
    }

    @Override
    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        incrementScoreFor(player);
        if (isWin(player)) {
            return Optional.of(player);
        }
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
        if (firstPlayerScore.equals(Point.ADVANTAGE) && (secondPlayerScore.ordinal() <= THIRTY_ORDINAL_NUMBER)) {
            return true;
        }
        return firstPlayerScore.equals(Point.GAME) && secondPlayerScore.equals(Point.FORTY);
    }

    private boolean isWinOfSecondPlayer() {
        if (secondPlayerScore.equals(Point.ADVANTAGE) && (firstPlayerScore.ordinal() <= THIRTY_ORDINAL_NUMBER)) {
            return true;
        }
        return secondPlayerScore.equals(Point.GAME) && firstPlayerScore.equals(Point.FORTY);
    }

    private void checkDeuce() {
        if (firstPlayerScore.equals(Point.ADVANTAGE) && secondPlayerScore.equals(Point.ADVANTAGE)) {
            firstPlayerScore = Point.FORTY;
            secondPlayerScore = Point.FORTY;
        }
    }
}
