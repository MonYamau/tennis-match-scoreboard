package com.project.controller;

import com.project.exception.IncorrectInputException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public class BaseServlet extends HttpServlet {
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
}
