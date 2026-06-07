package com.project.dto;

import java.util.List;

public record MatchPageDto(int currentPage, int pageCount, String namePattern, List<MatchDto> matches) {
}
