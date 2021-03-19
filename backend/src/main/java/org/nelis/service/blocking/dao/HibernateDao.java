package org.nelis.service.blocking.dao;

import java.util.List;

public interface HibernateDao<T> {
    T find(long id);
    List<T> findAll();
    void save(T type);
}
