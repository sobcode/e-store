package com.finalVariant.OnlineStore.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    boolean create(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

    void delete(T entity);

    boolean update(T entity);

    void close();
}
