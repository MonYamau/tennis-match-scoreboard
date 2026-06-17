package com.project.domain;

import com.project.domain.game.DefaultGame;
import com.project.domain.game.GameMode;
import com.project.domain.game.TieBreakGame;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TennisSet {
    private static final int PREPONDERANCE = 2;
    private static final int MIN_VALUE_SCORE = 6;
    private static final int TIE_BREAK_WIN = 7;

    private int firstPlayerScore;
    private int secondPlayerScore;
    private GameMode currentGame;

    public static TennisSet setupNewTennisSet() {
        return new TennisSet(0, 0, DefaultGame.setupNewDefaultGame());
    }

    public Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player) {
        Optional<PlayerNumber> gameWinner = currentGame.recalculateScoreFor(player);
        if (gameWinner.isEmpty()) {
            return Optional.empty();
        }
        incrementScoreFor(player);
        if (isWin(player)) {
            setupGameMode();
            return Optional.of(player);
        }
        setupGameMode();
        return Optional.empty();
    }

    private void incrementScoreFor(PlayerNumber playerNumber) {
        if (playerNumber.equals(PlayerNumber.FIRST_PLAYER)) {
            firstPlayerScore++;
        } else if (playerNumber.equals(PlayerNumber.SECOND_PLAYER)) {
            secondPlayerScore++;
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
        if (currentGame instanceof TieBreakGame) {
            return firstPlayerScore == TIE_BREAK_WIN;
        }
        if (currentGame instanceof DefaultGame) {
            return firstPlayerScore >= MIN_VALUE_SCORE && (firstPlayerScore - secondPlayerScore >= PREPONDERANCE);
        }
        throw new IllegalStateException("invalid state of the current game");
    }

    private boolean isWinOfSecondPlayer() {
        if (currentGame instanceof TieBreakGame) {
            return secondPlayerScore == TIE_BREAK_WIN;
        }
        if (currentGame instanceof DefaultGame) {
            return secondPlayerScore >= MIN_VALUE_SCORE && (secondPlayerScore - firstPlayerScore >= PREPONDERANCE);
        }
        throw new IllegalStateException("invalid state of the current game");
    }

    private void setupGameMode() {
        if (firstPlayerScore == MIN_VALUE_SCORE && secondPlayerScore == MIN_VALUE_SCORE) {
            currentGame = TieBreakGame.setupNewTieBreakGame();
        } else {
            currentGame = DefaultGame.setupNewDefaultGame();
        }
    }
}
