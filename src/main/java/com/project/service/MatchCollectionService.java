package com.project.service;

import com.project.dao.MatchDao;
import com.project.dto.MatchDto;
import com.project.dto.MatchPageDto;
import com.project.mapper.MatchMapper;
import com.project.model.Match;

import java.util.List;
import java.util.stream.Collectors;

public class MatchCollectionService {
    private final static int LIMIT_VALUE = 8;
    private final static int UNIT_PAGE = 1;

    private final MatchMapper mapper = MatchMapper.INSTANCE;
    private final MatchDao matchDao;

    public MatchCollectionService(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    public MatchPageDto findMatchesByFilters(int page, String namePattern) {
        boolean isEmptyFilter = namePattern == null || namePattern.isBlank();
        int pageCount = isEmptyFilter ? countPages() : countPagesWithFilter(namePattern);
        int correctPage = Math.min(page, pageCount);
        int offsetValue = (correctPage - 1) * LIMIT_VALUE;
        List<Match> matches = isEmptyFilter
                ? matchDao.findPage(offsetValue, LIMIT_VALUE)
                : matchDao.findPageByFilters(namePattern, offsetValue, LIMIT_VALUE);
        List<MatchDto> matchesDto = matches.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new MatchPageDto(correctPage, pageCount, namePattern, matchesDto);
    }

    private int countPages() {
        long countMatch = matchDao.countAll();
        if (countMatch % LIMIT_VALUE != 0) {
            return (int) ((countMatch / LIMIT_VALUE) + UNIT_PAGE);
        }
        return (int) (countMatch / LIMIT_VALUE);
    }

    private int countPagesWithFilter(String namePattern) {
        long countMatch = matchDao.countAllByFilter(namePattern);
        if (countMatch % LIMIT_VALUE != 0) {
            return (int) ((countMatch / LIMIT_VALUE) + UNIT_PAGE);
        }
        return (int) (countMatch / LIMIT_VALUE);
    }
}
