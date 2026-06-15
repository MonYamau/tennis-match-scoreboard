package com.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OngoingMatch {
    private final UUID uuid;
    private final int firstPlayerId;
    private final int secondPlayerId;
    private final String firstPlayerName;
    private final String secondPlayerName;
    private final TennisMatch currentMatch;
    private Integer winnerId;

    public void recalculateScoreForMatch(int playerId) {
        if (playerId == firstPlayerId) {
            Optional<PlayerNumber> player = currentMatch.recalculateScoreFor(PlayerNumber.FIRST_PLAYER);
            if (player.isPresent()) {
                winnerId = firstPlayerId;
            }
        } else if (playerId == secondPlayerId) {
            Optional<PlayerNumber> player = currentMatch.recalculateScoreFor(PlayerNumber.SECOND_PLAYER);
            if (player.isPresent()) {
                winnerId = secondPlayerId;
            }
        }
    }
}
