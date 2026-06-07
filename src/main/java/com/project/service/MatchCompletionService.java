package com.project.service;

import com.project.dao.MatchDao;
import com.project.dao.PlayerDao;
import com.project.dto.response.OngoingMatchDto;
import com.project.model.Match;
import com.project.model.Player;
import com.project.storage.MatchStorage;

import java.util.Optional;

public class MatchCompletionService {
    private final MatchDao matchDao;
    private final PlayerDao playerDao;
    private final MatchStorage matchStorage;

    public MatchCompletionService(MatchDao matchDao, PlayerDao playerDao, MatchStorage matchStorage) {
        this.matchDao = matchDao;
        this.playerDao = playerDao;
        this.matchStorage = matchStorage;
    }

    public void finishMatch(OngoingMatchDto matchDto) {
        matchStorage.delete(matchDto.uuid());
        Optional<Player> firstPlayer = playerDao.findByName(matchDto.firstPlayerName());
        Optional<Player> secondPlayer = playerDao.findByName(matchDto.secondPlayerName());
        if (firstPlayer.isEmpty() || secondPlayer.isEmpty()) {
            throw new RuntimeException();
        }
        Player winner = checkWinner(matchDto.winnerId(), firstPlayer.get(), secondPlayer.get());
        Match finishedMatch = new Match(firstPlayer.get(), secondPlayer.get(), winner);
        Optional<Match> result = matchDao.save(finishedMatch);
        if (result.isEmpty()) {
            throw new RuntimeException();
        }
    }

    private Player checkWinner(int winnerId, Player firstPlayer, Player secondPlayer) {
        if (winnerId == firstPlayer.getId()) {
            return firstPlayer;
        }
        return secondPlayer;
    }
}
