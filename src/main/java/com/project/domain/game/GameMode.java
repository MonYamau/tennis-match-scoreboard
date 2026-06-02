package com.project.domain.game;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.project.domain.PlayerNumber;

import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "gameMode"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = DefaultGame.class, name = "defaultGame"),
        @JsonSubTypes.Type(value = TieBreakGame.class, name = "tieBreakGame")
})
public interface GameMode {
    Optional<PlayerNumber> recalculateScoreFor(PlayerNumber player);
}
