package com.example.ejb.dao;

import java.util.Optional;

public abstract class GenericDAO<T> {

    public abstract Optional<T> findById(Object id);
}