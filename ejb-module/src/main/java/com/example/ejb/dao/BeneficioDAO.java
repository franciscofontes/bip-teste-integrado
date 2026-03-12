package com.example.ejb.dao;

import com.example.ejb.model.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class BeneficioDAO extends GenericDAO<Beneficio> {

    @PersistenceContext(unitName = "unit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transfer(Beneficio from, Beneficio to) {
        em.merge(from);
        em.merge(to);
    }
}
