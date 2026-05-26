package com.project.dao;

import com.project.model.Match;

import java.util.List;

public interface MatchDao extends BaseDao<Match> {
    List<Match> findAll();
}
