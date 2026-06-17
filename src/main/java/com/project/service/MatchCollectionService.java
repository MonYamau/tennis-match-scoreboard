package com.project.service;

import com.project.dao.MatchDao;
import com.project.dto.request.CollectionFilterDto;
import com.project.dto.response.MatchDto;
import com.project.dto.response.MatchPageDto;
import com.project.entity.Match;
import com.project.mapper.MatchMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MatchCollectionService {
    private final static int UNIT_PAGE = 1;

    private final MatchMapper mapper = MatchMapper.INSTANCE;
    private final MatchDao matchDao;

    public MatchPageDto findMatchesByFilters(CollectionFilterDto requestDto) {
        String pattern = requestDto.namePattern();
        int limit = requestDto.limitValue();
        boolean isEmptyFilter = pattern == null || pattern.isBlank();
        int pageCount = isEmptyFilter ? countPages(limit) : countPagesWithFilter(limit, pattern);
        int page = selectCorrectPage(requestDto.page(), pageCount);
        int offsetValue = (page - 1) * limit;
        List<Match> matches = isEmptyFilter
                ? matchDao.findPage(offsetValue, limit)
                : matchDao.findPageByFilters(pattern, offsetValue, limit);
        List<MatchDto> matchesDto = matches.stream().map(mapper::toDto).collect(Collectors.toList());
        return new MatchPageDto(page, pageCount, pattern, limit, matchesDto);
    }

    private int selectCorrectPage(int selectedPage, int pageCount) {
        if (pageCount >= UNIT_PAGE) {
            return Math.min(selectedPage, pageCount);
        }
        return UNIT_PAGE;
    }

    private int countPages(int limit) {
        long countMatch = matchDao.countAll();
        if (countMatch % limit != 0) {
            return (int) ((countMatch / limit) + UNIT_PAGE);
        }
        return (int) (countMatch / limit);
    }

    private int countPagesWithFilter(int limit, String namePattern) {
        long countMatch = matchDao.countAllByFilter(namePattern);
        if (countMatch % limit != 0) {
            return (int) ((countMatch / limit) + UNIT_PAGE);
        }
        return (int) (countMatch / limit);
    }
}
