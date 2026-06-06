package com.project.dao;

import com.project.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//РЕАЛИЗОВАТЬ SERVLET ФИЛЬТР ДЛЯ ТРАНЗАКЦИЙ
public class HibernateMatchDao implements Serializable, MatchDao {
    private final static String FINDING_QUERY = """
            SELECT m
            FROM Match m
            JOIN FETCH m.firstPlayer
            JOIN FETCH m.secondPlayer
            JOIN FETCH m.winner""";

    private final static String COUNTING_QUERY = """
            SELECT COUNT(m)
            FROM Match m
            JOIN FETCH m.firstPlayer
            JOIN FETCH m.secondPlayer
            JOIN FETCH m.winner""";

    private final static String QUERY_PATTERN_FILTER = """
            WHERE m.firstPlayer.name LIKE :pattern OR m.secondPlayer.name LIKE :pattern""";

    private final static String SORTING_QUERY = """
            LIMIT :limit ORDER BY m.id DESC""";

    private final SessionFactory sessionFactory;

    public HibernateMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Match> findPage(int index, int limit) {
        List<Match> matches;
        Session session = sessionFactory.getCurrentSession();
        matches = session.createQuery(FINDING_QUERY + SORTING_QUERY, Match.class)
                .setFirstResult(index)
                .setMaxResults(limit)
                .getResultList();
        return matches;
    }

    @Override
    public List<Match> findPageByFilters(String namePattern, int index, int limit) {
        List<Match> matches;
        Session session = sessionFactory.getCurrentSession();
        matches = session.createQuery(FINDING_QUERY + QUERY_PATTERN_FILTER + SORTING_QUERY, Match.class)
                .setParameter("pattern", namePattern)
                .setFirstResult(index)
                .setMaxResults(limit)
                .getResultList();
        return matches;
    }

    @Override
    public long countAll() {
        long counter;
        Session session = sessionFactory.getCurrentSession();
        counter = session.createQuery(COUNTING_QUERY, Long.class)
                .getResultCount();
        return counter;
    }

    @Override
    public long countAllByFilter(String namePattern) {
        long counter;
        Session session = sessionFactory.getCurrentSession();
        counter = session.createQuery(COUNTING_QUERY + QUERY_PATTERN_FILTER, Long.class)
                .setParameter("pattern", namePattern)
                .getResultCount();
        return counter;
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
