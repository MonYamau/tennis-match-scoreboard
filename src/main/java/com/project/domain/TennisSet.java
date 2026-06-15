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
        if (currentGame.getClass().equals(TieBreakGame.class)) {
            return firstPlayerScore == TIE_BREAK_WIN;
        }
        return firstPlayerScore >= MIN_VALUE_SCORE && (firstPlayerScore - secondPlayerScore >= PREPONDERANCE);
    }

    private boolean isWinOfSecondPlayer() {
        if (currentGame.getClass().equals(TieBreakGame.class)) {
            return secondPlayerScore == TIE_BREAK_WIN;
        }
        return secondPlayerScore >= MIN_VALUE_SCORE && (secondPlayerScore - firstPlayerScore >= PREPONDERANCE);
    }

    private void setupGameMode() {
        if (firstPlayerScore == MIN_VALUE_SCORE && secondPlayerScore == MIN_VALUE_SCORE) {
            currentGame = TieBreakGame.setupNewTieBreakGame();
        } else {
            currentGame = DefaultGame.setupNewDefaultGame();
        }
    }
}
