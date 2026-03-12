package com.example.ejb.service;

import com.example.ejb.dao.BeneficioDAO;
import com.example.ejb.exception.EntityNotFoundException;
import com.example.ejb.exception.TransferException;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @EJB
    private BeneficioDAO beneficioDAO;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        Beneficio from = beneficioDAO.find(Beneficio.class, fromId).orElseThrow(EntityNotFoundException::new);
        Beneficio to = beneficioDAO.find(Beneficio.class, toId).orElseThrow(EntityNotFoundException::new);

        if (amount.compareTo(from.getValor()) > 0) {
            throw new TransferException("Saldo insuficiente para a transferencia de " + amount);
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        beneficioDAO.transfer(from, to);
    }
}
