package com.project.dao;

import com.project.model.Player;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

//РЕАЛИЗОВАТЬ SERVLET ФИЛЬТР ДЛЯ ТРАНЗАКЦИЙ
public class HibernatePlayerDao implements PlayerDao {

    private final SessionFactory sessionFactory;

    public HibernatePlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Optional<Player> player = session.createQuery("FROM Player WHERE name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
        session.getTransaction().commit();
        return player;
    }

    @Override
    public List<Player> findByPatternWithMatches(String pattern) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        pattern = pattern + "%";
        List<Player> players = session.createQuery("FROM Player WHERE name LIKE :pattern", Player.class)
                .setParameter("pattern", pattern)
                .getResultList();
        for (Player player : players) {
            Hibernate.initialize(player.getMatchesByFirstPlayer());
            Hibernate.initialize(player.getMatchesBySecondPlayer());
        }
        session.getTransaction().commit();
        return players;
    }

    @Override
    public Optional<Player> save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Integer id = (Integer) session.save(player);
        player = session.get(Player.class, id);
        session.getTransaction().commit();
        return Optional.of(player);
    }
}
