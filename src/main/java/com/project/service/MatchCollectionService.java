package com.project.service;

import com.project.dao.MatchDao;
import com.project.dto.request.CollectionFilterDto;
import com.project.dto.response.MatchDto;
import com.project.dto.response.MatchPageDto;
import com.project.mapper.MatchMapper;
import com.project.model.Match;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MatchCollectionService {
    private final static int LIMIT_VALUE = 6;
    private final static int UNIT_PAGE = 1;

    private final MatchMapper mapper = MatchMapper.INSTANCE;
    private final MatchDao matchDao;

    public MatchPageDto findMatchesByFilters(CollectionFilterDto requestDto) {
        String pattern = requestDto.namePattern();
        boolean isEmptyFilter = pattern == null || pattern.isBlank();
        int pageCount = isEmptyFilter ? countPages() : countPagesWithFilter(pattern);
        int page = selectCorrectPage(requestDto.page(), pageCount);
        int offsetValue = (page - 1) * LIMIT_VALUE;
        List<Match> matches = isEmptyFilter
                ? matchDao.findPage(offsetValue, LIMIT_VALUE)
                : matchDao.findPageByFilters(pattern, offsetValue, LIMIT_VALUE);
        List<MatchDto> matchesDto = matches.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new MatchPageDto(page, pageCount, pattern, matchesDto);
    }

    private int selectCorrectPage(int selectedPage, int pageCount) {
        if (pageCount >= UNIT_PAGE) {
            return Math.min(selectedPage, pageCount);
        }
        return UNIT_PAGE;
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
