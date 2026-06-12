package com.project.filter;


import com.project.exception.*;
import com.project.util.JspPages;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            filterChain.doFilter(req, resp);
        } catch (IncorrectInputException e) {
            handleException(req, resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (DataNotFoundException e) {
            handleException(req, resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServletContextException | DatabaseException | StorageException | IllegalStateException e) {
            handleException(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            handleException(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unknown server error");
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, int statusCode, String message) throws ServletException, IOException {
        req.setAttribute("errorCode", statusCode);
        req.setAttribute("errorMessage", message);
        resp.setStatus(statusCode);
        if (statusCode == HttpServletResponse.SC_BAD_REQUEST) {
            String path = req.getParameter("jspForError");
            req.getRequestDispatcher(path).forward(req, resp);
        }
        if (statusCode == HttpServletResponse.SC_NOT_FOUND || statusCode == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            req.getRequestDispatcher(JspPages.ERROR).forward(req, resp);
        }
    }
}
