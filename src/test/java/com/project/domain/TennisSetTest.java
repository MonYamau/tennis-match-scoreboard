package com.project.domain;

import com.project.domain.game.DefaultGame;
import com.project.domain.game.TieBreakGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TennisSetTest {
    @Test
    public void shouldBeActivatedTieBreakGameMode() {
        TennisSet set = new TennisSet(5, 6, new DefaultGame(Point.FORTY, Point.ZERO));
        Optional<PlayerNumber> result = set.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(6, set.getFirstPlayerScore());
        Assertions.assertEquals(6, set.getSecondPlayerScore());
        Assertions.assertInstanceOf(TieBreakGame.class, set.getCurrentGame());
    }

    @Test
    public void shouldBeActivatedDefaultGameModeUponVictory() {
        TennisSet set = new TennisSet(6, 6, new TieBreakGame(6, 0));
        Optional<PlayerNumber> result = set.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(7, set.getFirstPlayerScore());
        Assertions.assertEquals(6, set.getSecondPlayerScore());
        Assertions.assertInstanceOf(DefaultGame.class, set.getCurrentGame());
    }

    @Test
    public void shouldGrantSetToPlayerWhenScoreIsFiveAndFourInDefaultGameMode() {
        TennisSet set = new TennisSet(5, 4, new DefaultGame(Point.FORTY, Point.ZERO));
        Optional<PlayerNumber> result = set.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(6, set.getFirstPlayerScore());
        Assertions.assertEquals(4, set.getSecondPlayerScore());
    }

    @Test
    public void shouldGrantSetToPlayerWhenScoreIsSixAndSixInTieBreakGameMode() {
        TennisSet set = new TennisSet(6, 6, new TieBreakGame(6, 0));
        Optional<PlayerNumber> result = set.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
        Assertions.assertEquals(PlayerNumber.FIRST_PLAYER, result.get());
        Assertions.assertEquals(7, set.getFirstPlayerScore());
        Assertions.assertEquals(6, set.getSecondPlayerScore());
    }
}
