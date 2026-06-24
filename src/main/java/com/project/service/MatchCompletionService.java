package com.project.service;

import com.project.dao.MatchDao;
import com.project.dao.PlayerDao;
import com.project.dto.response.OngoingMatchDto;
import com.project.entity.Match;
import com.project.entity.Player;
import com.project.storage.MatchStorage;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MatchCompletionService {
    private final MatchDao matchDao;
    private final PlayerDao playerDao;
    private final MatchStorage matchStorage;

    public void finishMatch(OngoingMatchDto matchDto) {
        Optional<Player> firstPlayer = playerDao.findByName(matchDto.firstPlayerName());
        Optional<Player> secondPlayer = playerDao.findByName(matchDto.secondPlayerName());
        if (firstPlayer.isEmpty() || secondPlayer.isEmpty()) {
            throw new IllegalStateException("Couldn't find the player to save");
        }
        Player winner = checkWinner(matchDto.winnerId(), firstPlayer.get(), secondPlayer.get());
        Match finishedMatch = new Match(firstPlayer.get(), secondPlayer.get(), winner);
        Optional<Match> result = matchDao.save(finishedMatch);
        if (result.isEmpty()) {
            throw new IllegalStateException("Couldn't find the finished match");
        }
        matchStorage.delete(matchDto.uuid());
    }

    private Player checkWinner(int winnerId, Player firstPlayer, Player secondPlayer) {
        if (winnerId == firstPlayer.getId()) {
            return firstPlayer;
        }
        if (winnerId == secondPlayer.getId()) {
            return secondPlayer;
        }
        throw new IllegalStateException("Couldn't determine the winner");
    }
}
