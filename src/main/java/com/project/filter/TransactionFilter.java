package com.project.filter;

import com.project.exception.DataNotFoundException;
import com.project.exception.IncorrectInputException;
import com.project.exception.ServletContextException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TransactionFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(TransactionFilter.class);

    SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.sessionFactory = (SessionFactory) filterConfig.getServletContext().getAttribute("SessionFactory");
        if (sessionFactory == null) {
            throw new ServletContextException("Couldn't find the session factory");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        int attempts = 3;

        while (attempts > 0) {
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                filterChain.doFilter(req, resp);
                transaction.commit();
                return;

            } catch (Exception exception) {
                handleRollback(transaction);

                if (exception instanceof IncorrectInputException || exception instanceof DataNotFoundException) {
                    throw exception;
                }

                if (isUniqueConstraintViolation(exception)) {
                    attempts--;
                    continue;
                }

                throw exception;
            }
        }
    }

    private void handleRollback(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception rollbackException) {
                log.error("Failed to rollback transaction", rollbackException);
            }
        }
    }

    private boolean isUniqueConstraintViolation(Throwable e) {
        while (e != null) {
            if (e instanceof ConstraintViolationException) {
                return true;
            }
            e = e.getCause();
        }
        return false;
    }
}
