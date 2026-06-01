package com.project.domain.game;

import com.project.domain.PlayerNumber;

import java.util.Optional;

public interface GameMode {
    Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player);
}
