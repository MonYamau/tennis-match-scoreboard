package com.project.controller;

import com.project.dto.request.OngoingMatchRequestDto;
import com.project.dto.response.OngoingMatchResponseDto;
import com.project.service.MatchRegistrationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MatchRegistrationServlet extends BaseServlet {
    private MatchRegistrationService registrationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.registrationService = (MatchRegistrationService) getServletContext().getAttribute("RegistrationService");
        if (registrationService == null) {
            throw new RuntimeException("Couldn't find the registration service");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = getNormalizedName(req, "firstPlayer");
        String secondPlayerName = getNormalizedName(req, "secondPlayer");

        OngoingMatchRequestDto requestDto = new OngoingMatchRequestDto(firstPlayerName, secondPlayerName);
        OngoingMatchResponseDto responseDto = registrationService.registerMatch(requestDto);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + responseDto.id());
    }
}
