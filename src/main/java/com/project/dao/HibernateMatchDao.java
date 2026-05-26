package com.project.dao;

import com.project.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateMatchDao implements MatchDao {

    private final SessionFactory sessionFactory;

    public HibernateMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Match> findAll() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Match", Match.class).getResultList();
        }
    }

    @Override
    public Optional<Match> add(Match model) {
        return Optional.empty();
    }
}
