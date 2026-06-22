package com.project.domain.game;

import com.project.domain.PlayerNumber;
import com.project.domain.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class DefaultGame implements GameMode {
    private Point firstPlayerScore;
    private Point secondPlayerScore;

    public static DefaultGame setupNewDefaultGame() {
        return new DefaultGame(Point.ZERO, Point.ZERO);
    }

    @Override
    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        if (isDeuce(player)) {
            firstPlayerScore = Point.FORTY;
            secondPlayerScore = Point.FORTY;
            return Optional.empty();
        }
        incrementScoreFor(player);
        if (isWin(player)) {
            return Optional.of(player);
        }
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
        if (firstPlayerScore.equals(Point.ADVANTAGE) && isScoreLessThanThirty(secondPlayerScore)) {
            return true;
        }
        return firstPlayerScore.equals(Point.GAME) && secondPlayerScore.equals(Point.FORTY);
    }

    private boolean isWinOfSecondPlayer() {
        if (secondPlayerScore.equals(Point.ADVANTAGE) && isScoreLessThanThirty(firstPlayerScore)) {
            return true;
        }
        return secondPlayerScore.equals(Point.GAME) && firstPlayerScore.equals(Point.FORTY);
    }

    private boolean isDeuce(PlayerNumber playerNumber) {
        if (playerNumber.equals(PlayerNumber.FIRST_PLAYER)) {
            return firstPlayerScore.equals(Point.FORTY) && secondPlayerScore.equals(Point.ADVANTAGE);
        }
        if (playerNumber.equals(PlayerNumber.SECOND_PLAYER)) {
            return firstPlayerScore.equals(Point.ADVANTAGE) && secondPlayerScore.equals(Point.FORTY);
        }
        throw new IllegalArgumentException("invalid value for player identification");
    }

    private boolean isScoreLessThanThirty(Point playerScore) {
        return playerScore.equals(Point.ZERO) || playerScore.equals(Point.FIFTEEN) || playerScore.equals(Point.THIRTY);
    }
}
