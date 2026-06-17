package com.project.controller;

import com.project.dto.request.CollectionFilterDto;
import com.project.dto.response.MatchPageDto;
import com.project.exception.ServletContextException;
import com.project.service.MatchCollectionService;
import com.project.util.JspPages;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MatchCollectionServlet extends BaseServlet {
    private MatchCollectionService matchCollectionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.matchCollectionService = (MatchCollectionService) getServletContext().getAttribute("CollectionService");
        if (matchCollectionService == null) {
            throw new ServletContextException("Couldn't find the collection service");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setJspForError(req, JspPages.MATCHES);
        CollectionFilterDto requestDto = getRequestDtoForGetMethod(req);
        MatchPageDto responseDto = matchCollectionService.findMatchesByFilters(requestDto);
        req.setAttribute("matchPage", responseDto);
        req.getRequestDispatcher(JspPages.MATCHES).forward(req, resp);
    }

    private CollectionFilterDto getRequestDtoForGetMethod(HttpServletRequest req) {
        int page = getNormalizedPage(req, "page");
        String namePattern = getNormalizedPattern(req, "filter_by_player_name");
        int limitValue = getNormalizedLimit(req, "limit");
        return new CollectionFilterDto(page, namePattern, limitValue);
    }
}
