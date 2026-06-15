package com.project.domain;

import com.project.domain.game.DefaultGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TennisMatchTest {
    @Test
    public void shouldContinueMatchWhenScoreIsZeroAndZero() {
        TennisSet set = new TennisSet(5, 0, new DefaultGame(Point.FORTY, Point.ZERO));
        TennisMatch match = new TennisMatch(0, 0, set);
        Optional<PlayerNumber> result = match.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(1, match.getFirstPlayerScore());
        Assertions.assertEquals(0, match.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantMatchToPlayerWhenScoreIsOneAndOne() {
        TennisSet set = new TennisSet(5, 0, new DefaultGame(Point.FORTY, Point.ZERO));
        TennisMatch match = new TennisMatch(1, 1, set);
        Optional<PlayerNumber> result = match.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(2, match.getFirstPlayerScore());
        Assertions.assertEquals(1, match.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantMatchToPlayerWhenScoreIsOneAndZero() {
        TennisSet set = new TennisSet(5, 0, new DefaultGame(Point.FORTY, Point.ZERO));
        TennisMatch match = new TennisMatch(1, 0, set);
        Optional<PlayerNumber> result = match.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(2, match.getFirstPlayerScore());
        Assertions.assertEquals(0, match.getSecondPlayerScore());
    }
}
