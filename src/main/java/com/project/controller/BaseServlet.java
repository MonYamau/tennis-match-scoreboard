package com.project.controller;

import com.project.exception.IncorrectInputException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public class BaseServlet extends HttpServlet {
    private final static int DEFAULT_PAGE = 1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected String getNormalizedName(HttpServletRequest req, String parameter) {
        String name = req.getParameter(parameter);
        if (name.isBlank()) {
            throw new IncorrectInputException("The expected parameter is empty");
        }
        return name.strip();
    }

    protected int getNormalizedPage(HttpServletRequest req, String parameter) {
        String pageParam = req.getParameter(parameter);
        if (pageParam == null) {
            return DEFAULT_PAGE;
        }
        int page = convertNumber(pageParam);
        validateNaturalNumber(page);
        return page;
    }

    private int convertNumber(String parameter) {
        try {
            return Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new IncorrectInputException("Incorrect number format (a natural number is expected)");
        }
    }

    private void validateNaturalNumber(int page) {
        if (page < DEFAULT_PAGE) {
            throw new IncorrectInputException("Incorrect number format (a natural number is expected)");
        }
    }
}
