package com.atm.repository;

import java.util.List;

public interface BaseRepository<T> {

    List<T> findAll();

    void saveAll(List<T> items);

    T findById(int id);

    void add(T item);
}
