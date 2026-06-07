package com.project.mapper;

import com.project.domain.Point;
import com.project.domain.game.DefaultGame;
import com.project.domain.game.GameMode;
import com.project.domain.game.TieBreakGame;
import com.project.dto.domain.CurrentGameDto;
import com.project.dto.domain.OngoingMatchDto;
import com.project.model.OngoingMatch;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OngoingMatchMapper {
    OngoingMatchMapper INSTANCE = Mappers.getMapper(OngoingMatchMapper.class);

    OngoingMatchDto toDto(OngoingMatch match);

    OngoingMatch toModel(OngoingMatchDto dto);

    default CurrentGameDto mapGameModeToDto(GameMode gameMode) {
        if (gameMode instanceof DefaultGame defaultGame) {
            return new CurrentGameDto(
                    "defaultGame",
                    defaultGame.getFirstPlayerScore().name(),
                    defaultGame.getSecondPlayerScore().name()
            );
        } else if (gameMode instanceof TieBreakGame tieBreakGame) {
            return new CurrentGameDto(
                    "tieBreakGame",
                    String.valueOf(tieBreakGame.getFirstPlayerScore()),
                    String.valueOf(tieBreakGame.getSecondPlayerScore())
            );
        }
        throw new IllegalArgumentException();
    }

    default GameMode mapDtoToGameMode(CurrentGameDto dto) {
        if ("defaultGame".equals(dto.gameMode())) {
            return new DefaultGame(
                    Point.valueOf(dto.firstPlayerScore()),
                    Point.valueOf(dto.secondPlayerScore())
            );
        } else if ("tieBreakGame".equals(dto.gameMode())) {
            return new TieBreakGame(
                    Integer.parseInt(dto.firstPlayerScore()),
                    Integer.parseInt(dto.secondPlayerScore())
            );
        }
        throw new IllegalArgumentException();
    }
}
