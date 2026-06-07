package com.project.dao;

import com.project.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

//РЕАЛИЗОВАТЬ SERVLET ФИЛЬТР ДЛЯ ТРАНЗАКЦИЙ
public class HibernatePlayerDao implements PlayerDao {

    private final SessionFactory sessionFactory;

    public HibernatePlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Player> findByName(String name) {
        Optional<Player> player;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        player = session.createQuery("FROM Player WHERE name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
        session.getTransaction().commit();
        return player;
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
