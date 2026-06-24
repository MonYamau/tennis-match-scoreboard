package com.project.storage;

import com.project.domain.OngoingMatch;

import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

public interface MatchStorage {

    void save(OngoingMatch match);

    Optional<OngoingMatch> find(UUID uuid);

    OngoingMatch update(UUID uuid, UnaryOperator<OngoingMatch> updater);

    void delete(UUID uuid);
}
