package com.project.dao;

import com.project.exception.DatabaseException;
import com.project.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class HibernateMatchDao implements Serializable, MatchDao {
    private final static String FINDING_QUERY = """
            SELECT m
            FROM Match m
            JOIN FETCH m.firstPlayer
            JOIN FETCH m.secondPlayer
            JOIN FETCH m.winner""";

    private final static String COUNTING_QUERY = """
            SELECT COUNT(m)
            FROM Match m""";

    private final static String QUERY_PATTERN_FILTER = """
            
            WHERE m.firstPlayer.name LIKE :pattern OR m.secondPlayer.name LIKE :pattern""";

    private final static String SORTING_QUERY = """
            
            ORDER BY m.id DESC""";

    private final SessionFactory sessionFactory;

    public HibernateMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Match> findPage(int index, int limit) {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            List<Match> matches = session.createQuery(FINDING_QUERY + SORTING_QUERY, Match.class)
                    .setFirstResult(index)
                    .setMaxResults(limit)
                    .getResultList();
            session.getTransaction().commit();
            return matches;

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to find page from the database\n" + e.getMessage());
        }
    }

    @Override
    public List<Match> findPageByFilters(String namePattern, int index, int limit) {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            List<Match> matches = session.createQuery(FINDING_QUERY + QUERY_PATTERN_FILTER + SORTING_QUERY, Match.class)
                    .setParameter("pattern", namePattern + "%")
                    .setFirstResult(index)
                    .setMaxResults(limit)
                    .getResultList();
            session.getTransaction().commit();
            return matches;

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to find page with filters from the database\n" + e.getMessage());
        }
    }

    @Override
    public long countAll() {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            long counter = session.createQuery(COUNTING_QUERY, Long.class)
                    .getSingleResult();
            session.getTransaction().commit();
            return counter;

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to count entities from the database\n" + e.getMessage());
        }
    }

    @Override
    public long countAllByFilter(String namePattern) {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            long counter = session.createQuery(COUNTING_QUERY + QUERY_PATTERN_FILTER, Long.class)
                    .setParameter("pattern", namePattern + "%")
                    .getSingleResult();
            session.getTransaction().commit();
            return counter;

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to count entities from the database with filters\n" + e.getMessage());
        }
    }

    @Override
    public Optional<Match> save(Match match) {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            Integer id = (Integer) session.save(match);
            match = session.get(Match.class, id);
            session.getTransaction().commit();
            return Optional.of(match);

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to save match to the database\n" + e.getMessage());
        }
    }
}
