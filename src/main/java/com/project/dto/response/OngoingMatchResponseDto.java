package com.project.dto.response;

import java.util.UUID;

public record OngoingMatchResponseDto(UUID id, String firstPlayerName, String secondPlayerName,
                                      CurrentMatchDto currentMatch) {
}
