package com.project.dao;

import com.project.entity.Match;
import com.project.exception.DatabaseException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
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

    @Override
    public List<Match> findPage(int index, int limit) {
        try {
            return sessionFactory.getCurrentSession().createQuery(FINDING_QUERY + SORTING_QUERY, Match.class)
                    .setFirstResult(index)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to find page from the database", originalException);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the database", e);
        }
    }

    @Override
    public List<Match> findPageByFilters(String namePattern, int index, int limit) {
        try {
            return sessionFactory.getCurrentSession()
                    .createQuery(FINDING_QUERY + QUERY_PATTERN_FILTER + SORTING_QUERY, Match.class)
                    .setParameter("pattern", namePattern + "%")
                    .setFirstResult(index)
                    .setMaxResults(limit)
                    .getResultList();

        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to find page with filters from the database", originalException);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the database", e);
        }
    }

    @Override
    public long countAll() {
        try {
            return sessionFactory.getCurrentSession().createQuery(COUNTING_QUERY, Long.class)
                    .getSingleResult();

        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to count entities from the database", originalException);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the database", e);
        }
    }

    @Override
    public long countAllByFilter(String namePattern) {
        try {
            return sessionFactory.getCurrentSession().createQuery(COUNTING_QUERY + QUERY_PATTERN_FILTER, Long.class)
                    .setParameter("pattern", namePattern + "%")
                    .getSingleResult();

        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to count entities from the database with filters", originalException);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the database", e);
        }
    }

    @Override
    public Optional<Match> save(Match match) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Integer id = (Integer) session.save(match);
            match = session.get(Match.class, id);
            return Optional.of(match);

        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to save match to the database", originalException);
        }
    }
}
