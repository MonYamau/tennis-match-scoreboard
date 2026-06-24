package com.project.service;

import com.project.domain.OngoingMatch;
import com.project.dto.request.OngoingMatchRequestDto;
import com.project.dto.response.OngoingMatchDto;
import com.project.exception.DataNotFoundException;
import com.project.mapper.OngoingMatchMapper;
import com.project.storage.MatchStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatchScoreService {
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;
    private final MatchStorage matchStorage;

    public OngoingMatchDto getMatch(OngoingMatchRequestDto requestDto) {
        OngoingMatch match = matchStorage.find(requestDto.uuid())
                .orElseThrow(() -> new DataNotFoundException("Couldn't find the current match"));
        return mapper.toDto(match);
    }

    public OngoingMatchDto recalculateMatch(OngoingMatchRequestDto requestDto) {
        OngoingMatch updatedMatch = matchStorage.update(requestDto.uuid(),
                (match -> {
                    match.recalculateScoreFor(requestDto.winnerId());
                    return match;
                }));
        return mapper.toDto(updatedMatch);
    }
}
