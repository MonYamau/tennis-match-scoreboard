package com.project.controller;

import com.project.exception.DataNotFoundException;
import com.project.exception.IncorrectInputException;
import com.project.util.ValidationUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public class BaseServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void setJspForError(HttpServletRequest req, String path) {
        req.setAttribute("jspForError", path);
    }

    protected String getNormalizedName(HttpServletRequest req, String parameter) {
        String name = req.getParameter(parameter);
        ValidationUtil.validateName(name);
        name = name.strip();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    protected String getNormalizedPattern(HttpServletRequest req) {
        String pattern = req.getParameter("filter_by_player_name");
        if (pattern != null) {
            return pattern.strip();
        }
        return null;
    }

    protected int getNormalizedNaturalNumber(HttpServletRequest req, String parameterName, int defaultValue) {
        String parameter = req.getParameter(parameterName);
        if (parameter == null) {
            return defaultValue;
        }
        int value = convertNumber(parameter);
        ValidationUtil.validateNaturalNumber(value);
        return value;
    }

    protected UUID getNormalizedUuid(HttpServletRequest req) {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null || uuidParam.isBlank()) {
            throw new DataNotFoundException("Expected UUID-parameter is missing");
        }
        try {
            return UUID.fromString(uuidParam);
        } catch (IllegalArgumentException e) {
            throw new IncorrectInputException("Incorrect UUID format");
        }
    }

    protected int getNormalizedWinnerId(HttpServletRequest req) {
        String idParam = req.getParameter("winnerId");
        ValidationUtil.validateParameter(idParam);
        return convertNumber(idParam);
    }

    private int convertNumber(String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Incorrect number format");
        }
    }
}
