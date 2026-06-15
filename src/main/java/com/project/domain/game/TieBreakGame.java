package com.project.domain.game;

import com.project.domain.PlayerNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TieBreakGame implements GameMode {
    private static final int PREPONDERANCE = 2;
    private static final int MIN_VALUE_SCORE = 7;

    private int firstPlayerScore;
    private int secondPlayerScore;

    public static TieBreakGame setupNewTieBreakGame() {
        return new TieBreakGame(0, 0);
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
