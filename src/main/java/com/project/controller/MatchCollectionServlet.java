package com.project.controller;

import com.project.dto.MatchPageDto;
import com.project.service.MatchCollectionService;
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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.parseInt(req.getParameter("page"));
        String namePattern = req.getParameter("filter_by_player_name");
        MatchPageDto dto = matchCollectionService.findMatchesByFilters(page, namePattern);
        req.setAttribute("matchPage", dto);
        req.getRequestDispatcher("WEB-INF/matches.jsp").forward(req, resp);
    }
}
