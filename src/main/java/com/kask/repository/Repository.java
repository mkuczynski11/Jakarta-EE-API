package com.kask.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {

    Optional<E> get(K id);

    List<E> getAll();

    void create(E entity);

    void delete(E entity);

    void update(E entity);
    void detach(E entity);
}
