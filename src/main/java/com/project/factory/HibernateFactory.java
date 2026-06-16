package com.project.factory;

import com.project.exception.ConfigurationException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateFactory {
    private static final Logger log = LoggerFactory.getLogger(HibernateFactory.class);

    public static SessionFactory create() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            log.error("Failed to initialize sessionFactory", e);
            throw new ConfigurationException("Failed to initialize sessionFactory", e);
        }
    }
}
