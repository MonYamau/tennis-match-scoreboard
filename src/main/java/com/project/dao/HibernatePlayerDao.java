package com.project.dao;

import com.project.entity.Player;
import com.project.exception.DatabaseException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

@RequiredArgsConstructor
public class HibernatePlayerDao implements PlayerDao {
    private final static String FINDING_QUERY = """
            FROM Player WHERE name = :name
            """;

    private final SessionFactory sessionFactory;

    @Override
    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            Optional<Player> player = session.createQuery(FINDING_QUERY, Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return player;

        } catch (PersistenceException originalException) {
            Exception exception = handleRollback(originalException, session.getTransaction());
            throw new DatabaseException("Failed to find player from the database", exception);
        } catch (Exception e) {
            Exception exception = handleRollback(e, session.getTransaction());
            throw new IllegalStateException("An unknown error occurred while working with the database", exception);
        }
    }

    @Override
    public Optional<Player> save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        try {

            session.beginTransaction();
            Integer id = (Integer) session.save(player);
            player = session.get(Player.class, id);
            session.getTransaction().commit();
            return Optional.of(player);

        } catch (PersistenceException originalException) {
            Exception exception = handleRollback(originalException, session.getTransaction());
            throw new DatabaseException("Failed to save player to the database", exception);
        } catch (Exception e) {
            Exception exception = handleRollback(e, session.getTransaction());
            throw new IllegalStateException("An unknown error occurred while working with the database", exception);
        }
    }

    private Exception handleRollback(Exception originalException, Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception rollbackException) {
                originalException.addSuppressed(rollbackException);
            }
        }
        return originalException;
    }
}
