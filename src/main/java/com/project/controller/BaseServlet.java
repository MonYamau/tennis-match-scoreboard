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
    private final static int DEFAULT_PAGE = 1;
    private final static int DEFAULT_LIMIT = 5;

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
        return name.strip();
    }

    protected String getNormalizedPattern(HttpServletRequest req, String parameter) {
        String pattern = req.getParameter(parameter);
        if (pattern != null) {
            return pattern.strip();
        }
        return null;
    }

    protected int getNormalizedPage(HttpServletRequest req, String parameter) {
        String pageParam = req.getParameter(parameter);
        if (pageParam == null) {
            return DEFAULT_PAGE;
        }
        int page = convertNumber(pageParam);
        ValidationUtil.validateNaturalNumber(page);
        return page;
    }

    protected int getNormalizedLimit(HttpServletRequest req, String parameter) {
        String limitParam = req.getParameter(parameter);
        if (limitParam == null) {
            return DEFAULT_LIMIT;
        }
        int limit = convertNumber(limitParam);
        ValidationUtil.validateNaturalNumber(limit);
        return limit;
    }

    protected UUID getNormalizedUuid(HttpServletRequest req, String parameter) {
        String uuidParam = req.getParameter(parameter);
        if (uuidParam == null || uuidParam.isBlank()) {
            throw new DataNotFoundException("Expected UUID-parameter is missing");
        }
        return UUID.fromString(uuidParam);
    }

    private int convertNumber(String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Incorrect number format");
        }
    }
}
