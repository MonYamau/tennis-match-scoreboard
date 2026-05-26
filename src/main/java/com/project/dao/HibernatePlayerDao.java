package com.project.dao;

import com.project.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernatePlayerDao implements PlayerDao {

    private final SessionFactory sessionFactory;

    public HibernatePlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Player> findByName(String name) {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Player WHERE name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Player> findByNameWithMatches(String name) {
        return List.of();
    }

    @Override
    public Optional<Player> add(Player model) {
        return Optional.empty();
    }
}
