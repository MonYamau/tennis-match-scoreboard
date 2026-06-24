package com.project.dao;

import com.project.entity.Player;
import com.project.exception.DatabaseException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class HibernatePlayerDao implements PlayerDao {
    private final static String FINDING_QUERY = """
            FROM Player WHERE name = :name
            """;

    private final SessionFactory sessionFactory;

    @Override
    public Optional<Player> findByName(String name) {
        try {
            return sessionFactory.getCurrentSession().createQuery(FINDING_QUERY, Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();

        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to find player from the database", originalException);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the database", e);
        }
    }

    @Override
    public Optional<Player> save(Player player) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Integer id = (Integer) session.save(player);
            player = session.get(Player.class, id);
            return Optional.of(player);

        } catch (PersistenceException originalException) {
            throw new DatabaseException("Failed to save player to the database", originalException);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the database", e);
        }
    }
}
