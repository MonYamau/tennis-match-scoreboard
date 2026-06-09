package com.project.controller;

import com.project.dto.MatchPageDto;
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
        int page = getNormalizedPage(req, "page");
        String namePattern = req.getParameter("filter_by_player_name");
        MatchPageDto dto = matchCollectionService.findMatchesByFilters(page, namePattern);
        req.setAttribute("matchPage", dto);
        req.getRequestDispatcher(JspPages.MATCHES).forward(req, resp);
    }
}
