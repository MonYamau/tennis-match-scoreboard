package com.project.factory;

import com.project.exception.ConfigurationException;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class HibernateFactory {
    private final Logger log = LoggerFactory.getLogger(HibernateFactory.class);

    public SessionFactory create() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            log.error("Failed to initialize sessionFactory", e);
            throw new ConfigurationException("Failed to initialize sessionFactory", e);
        }
    }
}
