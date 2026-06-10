package com.project.controller;

import com.project.dto.request.RegistrationDto;
import com.project.dto.response.OngoingMatchDto;
import com.project.exception.ServletContextException;
import com.project.service.MatchRegistrationService;
import com.project.util.JspPages;
import com.project.util.ValidationUtil;
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
            throw new ServletContextException("Couldn't find the registration service");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspPages.NEW_MATCH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationDto requestDto = getRequestDtoForPostMethod(req);
        OngoingMatchDto responseDto = registrationService.registerMatch(requestDto);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + responseDto.uuid());
    }

    private RegistrationDto getRequestDtoForPostMethod(HttpServletRequest req) {
        String firstPlayerName = getNormalizedName(req, "firstPlayer");
        String secondPlayerName = getNormalizedName(req, "secondPlayer");
        ValidationUtil.validateNamesForUnique(firstPlayerName, secondPlayerName);
        return new RegistrationDto(firstPlayerName, secondPlayerName);
    }
}
