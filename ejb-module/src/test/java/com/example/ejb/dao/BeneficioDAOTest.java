package com.example.ejb.dao;

import com.example.ejb.exception.BeneficioException;
import com.example.ejb.model.Beneficio;
import com.example.ejb.utils.MockUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeneficioDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private BeneficioDAO dao;

    @Test
    void shouldReturnBeneficioWhenIdExists() {

        var beneficio = MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class);
        var id = 1L;

        when(em.find(Beneficio.class, id)).thenReturn(beneficio);

        var beneficioOptional = dao.findById(id);

        assertTrue(beneficioOptional.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenIdNotExists() {
        var id = 3L;

        when(em.find(Beneficio.class, id)).thenReturn(null);

        var beneficioOptional = dao.findById(id);

        assertTrue(beneficioOptional.isEmpty());
    }

    @Test
    void shouldUpdateBeneficiosWhenValid() {
        var from = MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class);
        var to = MockUtils.fromFile("beneficio-valid-02.json", Beneficio.class);

        dao.updateBeneficios(from, to);

        verify(em, times(1)).merge(from);
        verify(em, times(1)).merge(to);
    }

    @Test
    void shouldNotUpdateBeneficiosWhenFail() {
        var from = MockUtils.fromFile("beneficio-valid-01.json", Beneficio.class);
        var to = MockUtils.fromFile("beneficio-valid-02.json", Beneficio.class);

        doThrow(new PersistenceException()).when(em).merge(any());

        assertThrows(BeneficioException.class, () -> dao.updateBeneficios(from, to));
    }
}