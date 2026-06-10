package com.project.service;

import com.project.dao.PlayerDao;
import com.project.domain.TennisMatch;
import com.project.dto.domain.OngoingMatchDto;
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

    public OngoingMatchDto registerMatch(String firstPlayerName, String secondPlayerName) {
        Player firstPlayer = savePlayer(firstPlayerName);
        Player secondPlayer = savePlayer(secondPlayerName);
        OngoingMatch match = new OngoingMatch(
                UUID.randomUUID(), firstPlayer.getId(), secondPlayer.getId(),
                firstPlayer.getName(), secondPlayer.getName(), new TennisMatch());
        matchStorage.save(match);
        return mapper.toDto(match);
    }

    private Player savePlayer(String playerName) {
        Optional<Player> result = playerDao.findByName(playerName);
        if (result.isPresent()) {
            return result.get();
        }
        Optional<Player> newPlayer = playerDao.save(new Player(playerName));
        if (newPlayer.isEmpty()) {
            throw new IllegalStateException("Couldn't find the player to register");
        }
        return newPlayer.get();
    }
}
