package com.project.dto.request;

import java.util.UUID;

public record OngoingMatchRequestDto(UUID uuid, Integer winnerId) {
}
