package com.project.dao;

import com.project.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//РЕАЛИЗОВАТЬ SERVLET ФИЛЬТР ДЛЯ ТРАНЗАКЦИЙ
public class HibernateMatchDao implements Serializable, MatchDao {

    private final SessionFactory sessionFactory;

    public HibernateMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Match> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Match> matches = session.createQuery("FROM Match", Match.class).getResultList();
        session.getTransaction().commit();
        return matches;
    }

    @Override
    public Optional<Match> save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(match);
        match = session.get(Match.class, id);
        session.getTransaction().commit();
        return Optional.of(match);
    }
}
