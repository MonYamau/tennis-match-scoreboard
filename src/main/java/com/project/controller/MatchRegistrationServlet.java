package com.project.controller;

import com.project.dto.domain.OngoingMatchDto;
import com.project.exception.ServletContextService;
import com.project.service.MatchRegistrationService;
import com.project.util.JspPages;
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
            throw new ServletContextService("Couldn't find the registration service");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPages.NEW_MATCH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstPlayerName = getNormalizedName(req, "firstPlayer");
        String secondPlayerName = getNormalizedName(req, "secondPlayer");

        OngoingMatchDto responseDto = registrationService.registerMatch(firstPlayerName, secondPlayerName);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + responseDto.uuid());
    }
}
