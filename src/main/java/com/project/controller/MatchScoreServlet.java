package com.project.controller;

import com.project.dto.domain.OngoingMatchDto;
import com.project.exception.ServletContextException;
import com.project.service.MatchCompletionService;
import com.project.service.MatchScoreService;
import com.project.util.JspPages;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public class MatchScoreServlet extends BaseServlet {
    private MatchScoreService matchScoreService;
    private MatchCompletionService matchCompletionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.matchScoreService = (MatchScoreService) getServletContext().getAttribute("ScoreService");
        this.matchCompletionService = (MatchCompletionService) getServletContext().getAttribute("CompletionService");
        if (matchScoreService == null) {
            throw new ServletContextException("Couldn't find the score service");
        }
        if (matchCompletionService == null) {
            throw new ServletContextException("Couldn't find the completion service");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        OngoingMatchDto dto = matchScoreService.getMatch(uuid);
        req.setAttribute("match", dto);
        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher(JspPages.MATCH_SCORE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        int winnerId = Integer.parseInt(req.getParameter("winnerId"));
        OngoingMatchDto dto = matchScoreService.recalculateMatch(uuid, winnerId);
        if (dto.winnerId() == null) {
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + dto.uuid());
            return;
        }
        matchCompletionService.finishMatch(dto);
        req.setAttribute("match", dto);
        req.getRequestDispatcher(JspPages.FINISHED_MATCH).forward(req, resp);
    }
}
