package com.project.service;

import com.project.dto.domain.OngoingMatchDto;
import com.project.exception.DataNotFoundException;
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

    public OngoingMatchDto getMatch(UUID uuid) {
        Optional<OngoingMatch> match = matchStorage.find(uuid);
        if (match.isEmpty()) {
            throw new DataNotFoundException("Couldn't find the current match");
        }
        return mapper.toDto(match.get());
    }

    public OngoingMatchDto recalculateMatch(UUID uuid, int winnerId) {
        OngoingMatchDto dto = getMatch(uuid);
        OngoingMatch match = mapper.toModel(dto);
        match.recalculateScoreForMatch(winnerId);
        matchStorage.save(match);
        return mapper.toDto(match);
    }
}
