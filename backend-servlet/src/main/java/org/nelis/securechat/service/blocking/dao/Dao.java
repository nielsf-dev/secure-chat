package org.nelis.securechat.service.blocking.dao;

import java.util.List;

public interface Dao<T> {
    T find(long id);
    List<T> findAll();
    void save(T type);
}
