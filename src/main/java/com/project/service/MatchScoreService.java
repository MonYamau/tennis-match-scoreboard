package com.project.service;

import com.project.domain.OngoingMatch;
import com.project.dto.request.OngoingMatchRequestDto;
import com.project.dto.response.OngoingMatchDto;
import com.project.exception.DataNotFoundException;
import com.project.mapper.OngoingMatchMapper;
import com.project.storage.MatchStorage;

import java.util.Optional;

public class MatchScoreService {
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;
    private final MatchStorage matchStorage;

    public MatchScoreService(MatchStorage matchStorage) {
        this.matchStorage = matchStorage;
    }

    public OngoingMatchDto getMatch(OngoingMatchRequestDto requestDto) {
        Optional<OngoingMatch> match = matchStorage.find(requestDto.uuid());
        if (match.isEmpty()) {
            throw new DataNotFoundException("Couldn't find the current match");
        }
        return mapper.toDto(match.get());
    }

    public OngoingMatchDto recalculateMatch(OngoingMatchRequestDto requestDto) {
        OngoingMatchDto dto = getMatch(requestDto);
        OngoingMatch match = mapper.toModel(dto);
        match.recalculateScoreForMatch(requestDto.winnerId());
        matchStorage.save(match);
        return mapper.toDto(match);
    }
}
