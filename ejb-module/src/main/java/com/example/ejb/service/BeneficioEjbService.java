package com.example.ejb.service;

import com.example.ejb.dao.BeneficioDAO;
import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import com.example.ejb.model.Page;
import com.example.ejb.utils.MessageUtils;
import com.example.ejb.validation.TransferValidation;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Stateless
public class BeneficioEjbService {

    private static final Logger logger = LogManager.getLogManager().getLogger(String.valueOf(BeneficioEjbService.class));

    @EJB
    private BeneficioDAO dao;

    public List<Beneficio> findAll() {
        return dao.findAll();
    }

    public Page<Beneficio> findByPage(Integer number, Integer size, String orderBy, String direction) {
        return dao.findByPage(number, size, orderBy, direction);
    }

    public Beneficio findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new BeneficioException(404, MessageUtils.BENEFICIO_NOT_RECORD));
    }

    public void create(Beneficio beneficio) {
        dao.create(beneficio);
    }

    public void update(Long id, Beneficio beneficio) {
        logger.info(String.format("Update id: %s, beneficio: %s", id, beneficio));
        var beneficioMerged = findById(id);
        beneficioMerged.setNome(beneficio.getNome());
        beneficioMerged.setDescricao(beneficio.getDescricao());
        beneficioMerged.setAtivo(beneficio.isAtivo());
        beneficioMerged.setValor(beneficio.getValor());
        dao.update(beneficioMerged);
    }

    public void delete(Long id) {
        dao.delete(findById(id));
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        var transferValidation = new TransferValidation();
        transferValidation.verifySameId(fromId, toId);

        var from = findById(fromId);
        var to = findById(toId);

        transferValidation.verifyInsufficientFunds(from.getValor(), amount);

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        dao.updateBeneficios(from, to);
    }
}
