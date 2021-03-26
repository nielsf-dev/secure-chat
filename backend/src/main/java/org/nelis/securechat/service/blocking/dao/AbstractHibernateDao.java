package org.nelis.securechat.service.blocking.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractHibernateDao<T> implements HibernateDao<T> {

    private final Class<T> type;
    private SessionFactory sessionFactory;

    public AbstractHibernateDao(SessionFactory sessionFactory, Class<T> type) {
        this.sessionFactory = sessionFactory;
        this.type = type;
    }

    @Override
    public T find(long id) {
        Session session = sessionFactory.getCurrentSession();
        T user = session.find(type, id);
        return user;
    }

    @Override
    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);

        Query<T> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public void save(T object) {
        Session session = sessionFactory.getCurrentSession();
        session.save(object);
    }
}
