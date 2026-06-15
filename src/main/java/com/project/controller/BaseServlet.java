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
        ValidationUtil.validatePage(page, DEFAULT_PAGE);
        return page;
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
            throw new IncorrectInputException("Incorrect number format (a natural number is expected)");
        }
    }
}
