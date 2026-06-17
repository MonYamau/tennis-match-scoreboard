package com.project.mapper;

import com.project.dto.response.MatchDto;
import com.project.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchMapper {
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(target = "firstPlayerName", source = "firstPlayer.name")
    @Mapping(target = "secondPlayerName", source = "secondPlayer.name")
    @Mapping(target = "winnerName", source = "winner.name")
    MatchDto toDto(Match match);
}
