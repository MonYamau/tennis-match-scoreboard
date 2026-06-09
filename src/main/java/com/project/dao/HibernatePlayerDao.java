package com.project.dao;

import com.project.exception.DatabaseException;
import com.project.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class HibernatePlayerDao implements PlayerDao {
    private final static String FINDING_QUERY = """
            FROM Player WHERE name = :name
            """;

    private final SessionFactory sessionFactory;

    public HibernatePlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to find player from the database\n" + e.getMessage());
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

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DatabaseException("Failed to save player to the database\n" + e.getMessage());
        }
    }
}
