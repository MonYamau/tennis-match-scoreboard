package com.project.dto.response;

import java.util.UUID;

public record OngoingMatchDto(UUID uuid, int firstPlayerId, int secondPlayerId, String firstPlayerName,
                              String secondPlayerName, Integer winnerId, CurrentMatchDto currentMatch) {
}
