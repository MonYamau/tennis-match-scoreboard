package com.project.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dao.HibernateMatchDao;
import com.project.dao.HibernatePlayerDao;
import com.project.dao.MatchDao;
import com.project.dao.PlayerDao;
import com.project.service.MatchCollectionService;
import com.project.service.MatchCompletionService;
import com.project.service.MatchRegistrationService;
import com.project.service.MatchScoreService;
import com.project.storage.MatchStorage;
import com.project.storage.RedisMatchStorage;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import redis.clients.jedis.RedisClient;

public class AppContextListener implements ServletContextListener {
    private SessionFactory sessionFactory;
    private RedisClient redisClient;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectMapper objectMapper = new ObjectMapper();
        sessionFactory = new Configuration().configure().buildSessionFactory();
        redisClient = RedisClient.builder().hostAndPort("localhost", 6379).build();

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
}
