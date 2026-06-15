package com.project.service;

import com.project.dao.PlayerDao;
import com.project.domain.OngoingMatch;
import com.project.domain.TennisMatch;
import com.project.dto.request.RegistrationDto;
import com.project.dto.response.OngoingMatchDto;
import com.project.mapper.OngoingMatchMapper;
import com.project.model.Player;
import com.project.storage.MatchStorage;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchRegistrationService {
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;
    private final MatchStorage matchStorage;
    private final PlayerDao playerDao;

    public OngoingMatchDto registerMatch(RegistrationDto registrationDto) {
        Player firstPlayer = savePlayer(registrationDto.firstPlayerName());
        Player secondPlayer = savePlayer(registrationDto.secondPlayerName());
        OngoingMatch match = new OngoingMatch(
                UUID.randomUUID(), firstPlayer.getId(), secondPlayer.getId(),
                firstPlayer.getName(), secondPlayer.getName(), TennisMatch.setupNewTennisMatch(), null);
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
