package com.project.controller;

import com.project.dto.response.OngoingMatchResponseDto;
import com.project.service.MatchScoreService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public class MatchScoreServlet extends BaseServlet {
    private MatchScoreService matchScoreService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.matchScoreService = (MatchScoreService) getServletContext().getAttribute("ScoreService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        OngoingMatchResponseDto responseDto = matchScoreService.getMatch(uuid);
        req.setAttribute("match", responseDto);
        req.setAttribute("uuid", uuid);
        req.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
