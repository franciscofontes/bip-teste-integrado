package com.example.ejb.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class GenericDAO<T> {

    @PersistenceContext(unitName = "unit")
    private EntityManager em;

    public List<T> findAll(Class<T> entityClass) {
        return em.createQuery("SELECT entity FROM " + entityClass.getSimpleName() + " entity", entityClass)
                .getResultList();
    }

    public Optional<T> find(Class<T> entityClass, Object id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }
}