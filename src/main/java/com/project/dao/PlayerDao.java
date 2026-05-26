package com.project.dao;

import com.project.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerDao extends BaseDao<Player> {
    Optional<Player> findByName(String name);

    List<Player> findByNameWithMatches(String name);
}
