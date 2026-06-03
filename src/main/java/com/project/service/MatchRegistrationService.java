package com.project.service;

import com.project.dao.PlayerDao;
import com.project.domain.TennisMatch;
import com.project.dto.request.OngoingMatchRequestDto;
import com.project.dto.response.OngoingMatchResponseDto;
import com.project.mapper.OngoingMatchMapper;
import com.project.model.OngoingMatch;
import com.project.model.Player;
import com.project.storage.MatchStorage;

import java.util.Optional;
import java.util.UUID;

public class MatchRegistrationService {
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;
    private final MatchStorage matchStorage;
    private final PlayerDao playerDao;

    public MatchRegistrationService(MatchStorage matchStorage, PlayerDao playerDao) {
        this.matchStorage = matchStorage;
        this.playerDao = playerDao;
    }

    public OngoingMatchResponseDto registerMatch(OngoingMatchRequestDto requestDto) {
        Player firstPlayer = savePlayer(requestDto.firstPlayerName());
        Player secondPlayer = savePlayer(requestDto.secondPlayerName());
        OngoingMatch match = new OngoingMatch(
                UUID.randomUUID(), firstPlayer.getId(), secondPlayer.getId(),
                firstPlayer.getName(), secondPlayer.getName(), new TennisMatch());
        matchStorage.saveMatch(match);
        return mapper.toDto(match);
    }

    private Player savePlayer(String playerName) {
        Optional<Player> result = playerDao.findByName(playerName);
        if (result.isPresent()) {
            return result.get();
        }

        Optional<Player> newPlayer = playerDao.save(new Player(playerName));
        if (newPlayer.isEmpty()) {
            throw new RuntimeException();
        }
        return newPlayer.get();
    }
}
