package com.example.ejb.service;

import com.example.ejb.dao.BeneficioDAO;
import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class BeneficioEjbService {

    @EJB
    private BeneficioDAO dao;

    public List<Beneficio> findAll() {
        return dao.findAll();
    }

    public Beneficio findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new BeneficioException(404, "Beneficio não cadastrado"));
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        var from = findById(fromId);
        var to = findById(toId);

        if (amount.compareTo(from.getValor()) > 0) {
            throw new BeneficioException(422, "Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        dao.updateBeneficios(from, to);
    }
}
