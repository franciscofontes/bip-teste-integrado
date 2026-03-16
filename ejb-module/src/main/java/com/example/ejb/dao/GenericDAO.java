package com.example.ejb.dao;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<T> {

    public abstract List<T> findAll();
    public abstract Optional<T> findById(Object id);
    public abstract void create(T t);
    public abstract void update(T t);
    public abstract void delete(T t);
}