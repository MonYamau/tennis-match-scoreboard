package com.project.dto.response;

import java.util.List;

public record MatchPageDto(int currentPage, int pageCount, String namePattern, List<MatchDto> matches) {
}
