package com.project.domain.game;

import com.project.domain.PlayerNumber;
import com.project.domain.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class DefaultGameTest {
    @Test
    public void shouldGrantAdvantageToPlayerWhenScoreIsDeuce() {
        DefaultGame game = new DefaultGame(Point.FORTY, Point.FORTY);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(Point.ADVANTAGE, game.getFirstPlayerScore());
        Assertions.assertEquals(Point.FORTY, game.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantGameToPlayerWhenScoreIsFortyAndZero() {
        DefaultGame game = new DefaultGame(Point.FORTY, Point.ZERO);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(Point.ADVANTAGE, game.getFirstPlayerScore());
        Assertions.assertEquals(Point.ZERO, game.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantGameToPlayerWhenScoreIsAdvantageAndForty() {
        DefaultGame game = new DefaultGame(Point.ADVANTAGE, Point.FORTY);
        Optional<PlayerNumber> result = game.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(Point.GAME, game.getFirstPlayerScore());
        Assertions.assertEquals(Point.FORTY, game.getSecondPlayerScore());
    }
}
