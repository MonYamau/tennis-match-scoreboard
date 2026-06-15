package com.project.domain.game;

import com.project.domain.PlayerNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TieBreakGameTest {
    @Test
    public void shouldContinueGameWhenScoreIsSevenAndSeven() {
        TieBreakGame game = new TieBreakGame(6, 7);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(7, game.getFirstPlayerScore());
        Assertions.assertEquals(7, game.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantGameToPlayerWhenScoreIsSixAndZero() {
        TieBreakGame game = new TieBreakGame(6, 0);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(7, game.getFirstPlayerScore());
        Assertions.assertEquals(0, game.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantGameToPlayerWhenScoreIsSixAndFive() {
        TieBreakGame game = new TieBreakGame(6, 5);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(7, game.getFirstPlayerScore());
        Assertions.assertEquals(5, game.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantGameToPlayerWhenScoreIsEightAndSeven() {
        TieBreakGame game = new TieBreakGame(8, 7);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(9, game.getFirstPlayerScore());
        Assertions.assertEquals(7, game.getSecondPlayerScore());
    }
}
