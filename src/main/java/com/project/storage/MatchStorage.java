package com.project.storage;

import com.project.domain.OngoingMatch;

import java.util.Optional;
import java.util.UUID;

public interface MatchStorage {

    void save(OngoingMatch match);

    Optional<OngoingMatch> find(UUID uuid);

    void delete(UUID uuid);
}
