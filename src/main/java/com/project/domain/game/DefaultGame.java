package com.project.domain.game;

import com.project.domain.PlayerNumber;
import com.project.domain.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class DefaultGame implements GameMode {
    private static final int THIRTY_ORDINAL_NUMBER = Point.THIRTY.ordinal();

    private Point firstPlayerScore;
    private Point secondPlayerScore;

    public static DefaultGame setupNewDefaultGame() {
        return new DefaultGame(Point.ZERO, Point.ZERO);
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
        } else if (player.equals(PlayerNumber.SECOND_PLAYER)) {
            secondPlayerScore = secondPlayerScore.next();
        } else {
            throw new IllegalArgumentException("invalid value for counter increment");
        }
    }

    private boolean isWin(PlayerNumber playerNumber) {
        if (playerNumber.equals(PlayerNumber.FIRST_PLAYER)) {
            return isWinOfFirstPlayer();
        }
        if (playerNumber.equals(PlayerNumber.SECOND_PLAYER)) {
            return isWinOfSecondPlayer();
        }
        throw new IllegalArgumentException("invalid value for player identification");
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
