package com.project.service;

import com.project.dto.response.OngoingMatchResponseDto;
import com.project.mapper.OngoingMatchMapper;
import com.project.model.OngoingMatch;
import com.project.storage.MatchStorage;

import java.util.Optional;
import java.util.UUID;

public class MatchScoreService {
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;
    private final MatchStorage matchStorage;

    public MatchScoreService(MatchStorage matchStorage) {
        this.matchStorage = matchStorage;
    }

    public OngoingMatchResponseDto getMatch(UUID uuid) {
        Optional<OngoingMatch> match = matchStorage.getMatch(uuid);
        if (match.isEmpty()) {
            throw new RuntimeException();
        }
        return mapper.toDto(match.get());
    }
}
