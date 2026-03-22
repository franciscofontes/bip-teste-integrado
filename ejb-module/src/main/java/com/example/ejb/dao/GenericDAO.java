package com.example.ejb.dao;

import com.example.ejb.model.Page;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAO<T> {

    public abstract List<T> findAll();
    public abstract Page<T> findByPage(Integer pageNumber, Integer pageSize, String orderBy, String direction);
    public abstract Optional<T> findById(Object id);
    public abstract void create(T t);
    public abstract void update(T t);
    public abstract void delete(T t);
}