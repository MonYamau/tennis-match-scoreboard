package com.project.dao;

import com.project.entity.Match;

import java.util.List;

public interface MatchDao extends BaseDao<Match> {
    List<Match> findPage(int index, int limit);

    List<Match> findPageByFilters(String namePattern, int index, int limit);

    long countAll();

    long countAllByFilter(String namePattern);
}
