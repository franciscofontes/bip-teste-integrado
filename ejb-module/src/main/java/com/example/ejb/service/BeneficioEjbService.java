package com.example.ejb.service;

import com.example.ejb.dao.BeneficioDAO;
import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @EJB
    private BeneficioDAO dao;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        Beneficio from = find(fromId);
        Beneficio to = find(toId);
        
        if (amount.compareTo(from.getValor()) > 0) {
            throw new BeneficioException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        dao.transfer(from, to);
    }
    
    public void save(Beneficio beneficio) {
        dao.save(beneficio);
    }

    public Beneficio find(Long id) {
        return dao.find(Beneficio.class, id).orElseThrow(() -> new BeneficioException("Beneficio não cadastrado"));
    }   
}
