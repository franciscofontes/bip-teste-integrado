package com.example.ejb.dao;

import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private BeneficioDAO dao;

    @BeforeEach
    void setUp() {
        dao = new BeneficioDAO(em, Beneficio.class);
    }

    @Test
    void shouldReturnBeneficioWhenIdExists() {
        Beneficio beneficio = new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal("1000.00"), true);

        when(em.find(Beneficio.class, 1L)).thenReturn(beneficio);

        Optional<Beneficio> beneficioOptional = dao.findById(1L);

        assertTrue(beneficioOptional.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenIdNotExists() {
        when(em.find(Beneficio.class, 3L)).thenReturn(null);

        Optional<Beneficio> beneficioOptional = dao.findById(3L);

        assertTrue(beneficioOptional.isEmpty());
    }

    @Test
    void shouldUpdateBeneficiosWhenValid() {
        var from = new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal("1000.00"), true);
        var to = new Beneficio(2L, "Beneficio B", "Descrição B", new BigDecimal("500.00"), true);

        dao.setEntityManager(em);

        dao.updateBeneficios(from, to);

        verify(em, times(1)).merge(from);
        verify(em, times(1)).merge(to);
    }

    @Test
    void shouldNotUpdateBeneficiosWhenFail() {
        var from = new Beneficio(1L, "Beneficio A", "Descrição A", new BigDecimal("1000.00"), true);
        var to = new Beneficio(2L, "Beneficio B", "Descrição B", new BigDecimal("500.00"), true);

        doThrow(new PersistenceException()).when(em).merge(any());

        dao.setEntityManager(em);
        assertThrows(BeneficioException.class, () -> dao.updateBeneficios(from, to));
    }
}