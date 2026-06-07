package com.project.dao;

import com.project.model.Player;

import java.util.Optional;

public interface PlayerDao extends BaseDao<Player> {
    Optional<Player> findByName(String name);
}
