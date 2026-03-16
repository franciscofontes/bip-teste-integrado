package com.example.ejb.dao;

import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

@Stateless
public class BeneficioDAO extends GenericDAO<Beneficio> {

    @PersistenceContext(unitName = "unit")
    private EntityManager em;

    @Override
    public List<Beneficio> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + Beneficio.class.getSimpleName() + " e", Beneficio.class).getResultList();
        } catch (PersistenceException e) {
            throw new BeneficioException(500, "Erro ao tentar buscar beneficios");
        }
    }

    @Override
    public Optional<Beneficio> findById(Object id) {
        try {
            return Optional.ofNullable(em.find(Beneficio.class, id));
        } catch (PersistenceException e) {
            throw new BeneficioException(500, "Erro ao tentar buscar beneficio");
        }
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
}
