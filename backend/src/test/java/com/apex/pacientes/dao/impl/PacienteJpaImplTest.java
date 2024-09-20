package com.apex.pacientes.dao.impl;

import com.apex.pacientes.model.Paciente;
import com.apex.pacientes.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteJpaImplTest {

    @InjectMocks
    private PacienteJpaImpl underTest;
    @Mock
    private PacienteRepository repository;

    @Test
    void salvar() {

        Paciente paciente = new Paciente();

        underTest.salvar(paciente);

        verify(repository).save(paciente);
    }

    @Test
    void excluir() {
        Long id = 1L;

        underTest.excluir(id);

        verify(repository).deleteById(id);
    }

    @Test
    void buscar() {

        Long id = 1L;

        underTest.buscar(id);

        verify(repository).findById(id);
    }

    @Test
    void listar() {

        List<Paciente> pacientes = List.of(Paciente.builder().id(1L).nome("Teste 1").build(),
                Paciente.builder().id(2L).nome("Teste 2").build());

        Pageable unpaged = Pageable.unpaged();
        when(repository.findAll(unpaged)).thenReturn(new PageImpl<>(pacientes));

        Page<Paciente> resultado = underTest.listar(unpaged);

        assertThat(resultado.getContent()).isEqualTo(pacientes);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository).findAll(pageableCaptor.capture());

    }

    @Test
    void existePorEmail() {

        String email = "teste@gmail.com";

        underTest.existePorEmail(email);

        verify(repository).existsByEmail(email);

    }

    @Test
    void existePorId() {

        Long id = 1L;

        underTest.existePorId(id);

        verify(repository).existsById(id);
    }
}