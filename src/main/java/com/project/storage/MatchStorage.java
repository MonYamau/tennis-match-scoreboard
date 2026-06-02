package com.project.storage;

import com.project.model.OngoingMatch;

import java.util.Optional;
import java.util.UUID;

public interface MatchStorage {

    void saveMatch(OngoingMatch match);

    Optional<OngoingMatch> getMatch(UUID uuid);

    void deleteMatch(UUID uuid);
}
