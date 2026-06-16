package com.project.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dao.HibernateMatchDao;
import com.project.dao.HibernatePlayerDao;
import com.project.dao.MatchDao;
import com.project.dao.PlayerDao;
import com.project.exception.ServletContextException;
import com.project.filter.ExceptionFilter;
import com.project.service.MatchCollectionService;
import com.project.service.MatchCompletionService;
import com.project.service.MatchRegistrationService;
import com.project.service.MatchScoreService;
import com.project.storage.MatchStorage;
import com.project.storage.RedisMatchStorage;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.RedisClient;

public class AppContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(AppContextListener.class);

    private final static String HOST = "localhost";
    private final static int PORT = 6379;
    private SessionFactory sessionFactory;
    private RedisClient redisClient;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectMapper objectMapper = new ObjectMapper();
        initialize();

        PlayerDao playerDao = new HibernatePlayerDao(sessionFactory);
        MatchDao matchDao = new HibernateMatchDao(sessionFactory);
        MatchStorage matchStorage = new RedisMatchStorage(objectMapper, redisClient);

        MatchRegistrationService registrationService = new MatchRegistrationService(matchStorage, playerDao);
        MatchScoreService scoreService = new MatchScoreService(matchStorage);
        MatchCompletionService completionService = new MatchCompletionService(matchDao, playerDao, matchStorage);
        MatchCollectionService collectionService = new MatchCollectionService(matchDao);

        ServletContext context = sce.getServletContext();
        context.setAttribute("RegistrationService", registrationService);
        context.setAttribute("ScoreService", scoreService);
        context.setAttribute("CompletionService", completionService);
        context.setAttribute("CollectionService", collectionService);

        log.info("Successful application initialization");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        if (redisClient != null) {
            redisClient.close();
        }
    }

    private void initialize() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            log.error("Failed to initialize sessionFactory", e);
            throw new ServletContextException("Failed to initialize sessionFactory");
        }
        try {
            redisClient = RedisClient.builder().hostAndPort(HOST, PORT).build();
            redisClient.ping();
        } catch (Exception e) {
            log.error("Failed to initialize redisClient", e);
            throw new ServletContextException("Failed to initialize redisClient");
        }
    }
}
