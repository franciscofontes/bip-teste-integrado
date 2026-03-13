package com.example.ejb.dao;

import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

@Stateless
public class BeneficioDAO extends GenericDAO<Beneficio> {

    @PersistenceContext(unitName = "unit")
    private EntityManager em;

    public BeneficioDAO(EntityManager em, Class<Beneficio> entityClass) {
        super(em, entityClass);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateBeneficios(Beneficio from, Beneficio to) {
        try {
            em.merge(from);
            em.merge(to);
        } catch (PersistenceException e) {
           throw new BeneficioException(500, "Erro ao tentar atualizar beneficios");
        }
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
