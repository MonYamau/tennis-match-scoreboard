package com.project.dto.response;

import java.util.UUID;

public record OngoingMatchResponseDto(UUID id, int firstPlayerId, int secondPlayerId, String firstPlayerName,
                                      String secondPlayerName, CurrentMatchDto currentMatch) {
}
