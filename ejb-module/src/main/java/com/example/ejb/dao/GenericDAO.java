package com.example.ejb.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;

import java.util.Optional;

@Stateless
public abstract class GenericDAO<T> {

    protected EntityManager em;
    private final Class<T> entityClass;

    public GenericDAO(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public Optional<T> findById(Object id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }
}