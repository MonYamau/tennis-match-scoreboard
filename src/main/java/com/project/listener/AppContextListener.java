package com.project.listener;

import com.project.dao.HibernateMatchDao;
import com.project.dao.HibernatePlayerDao;
import com.project.util.HibernateUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.hibernate.SessionFactory;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        HibernatePlayerDao playerDao = new HibernatePlayerDao(sessionFactory);
        HibernateMatchDao matchDao = new HibernateMatchDao(sessionFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.closeSessionFactory();
    }
}
