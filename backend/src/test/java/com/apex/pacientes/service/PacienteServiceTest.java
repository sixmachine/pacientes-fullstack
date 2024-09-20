package com.apex.pacientes.service;

import com.apex.pacientes.dao.PacienteDAO;
import com.apex.pacientes.dto.PacienteDTO;
import com.apex.pacientes.enums.Sexo;
import com.apex.pacientes.exception.DuplicateResourceException;
import com.apex.pacientes.exception.ResourceNotFoundException;
import com.apex.pacientes.mapper.PacienteMapper;
import com.apex.pacientes.model.Paciente;
import com.apex.pacientes.request.PacienteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    private PacienteService underTest;
    @Mock
    private PacienteDAO dao;
    private PacienteMapper mapper = new PacienteMapper();

    @BeforeEach
    void setUp() {
        underTest = new PacienteService(dao, mapper);
    }

    @Test
    void iraAcharUmPacientePorId() {

        Paciente paciente = new Paciente(1L, "Teste 1", "teste@gmail.com", LocalDate.now(), Sexo.M);
        PacienteDTO esperado = mapper.apply(paciente);
        Long id = 1L;

        when(dao.buscar(id)).thenReturn(Optional.of(paciente));

        PacienteDTO resultado = underTest.buscarPorId(id);

        assertThat(resultado).isEqualTo(esperado);

    }

    @Test
    void iraLancarUmExcecaoAoNaoAcharPacientePorId() {

        Long id = 1L;

        when(dao.buscar(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.buscarPorId(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Paciente [1] nao encontrado");

    }

    @Test
    void iraListarPacientes() {

        Pageable unpaged = Pageable.unpaged();

        when(dao.listar(unpaged)).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThat(underTest.listar(unpaged)).isEmpty();

    }

    @Test
    void iraExcluirUmPacientePorId() {

        Long id = 1L;

        when(dao.existePorId(id)).thenReturn(true);

        underTest.excluir(id);

        verify(dao).excluir(id);

    }

    @Test
    void iraLancarExcecaoAoExcluirPacientePorId() {

        Long id = 1L;

        when(dao.existePorId(id)).thenReturn(false);

        assertThatThrownBy(() -> underTest.excluir(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Paciente [1] nao encontrado");

        verify(dao, never()).excluir(id);

    }

    @Test
    void iraSalvarUmPaciente() {

        PacienteRequest novoPaciente = PacienteRequest.builder()
                .nome("Teste")
                .sexo(Sexo.M)
                .email("email@gmail.com")
                .dataNascimento(LocalDate.now())
                .build();

        Paciente salvado = new Paciente(1L, "Teste 1", "teste@gmail.com", LocalDate.now(), Sexo.M);

        when(dao.existePorEmail(novoPaciente.getEmail())).thenReturn(false);

        when(dao.salvar(any())).thenReturn(salvado);

        underTest.adicionar(novoPaciente);

        ArgumentCaptor<Paciente> argumentCaptor = ArgumentCaptor.forClass(Paciente.class);
        verify(dao).salvar(argumentCaptor.capture());

    }


    @Test
    void iraLancarUmaExcecaoAoSalvarUmPaciente() {

        PacienteRequest novoPaciente = PacienteRequest.builder()
                .nome("Teste")
                .sexo(Sexo.M)
                .email("email@gmail.com")
                .dataNascimento(LocalDate.now())
                .build();

        when(dao.existePorEmail(novoPaciente.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> underTest.adicionar(novoPaciente))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email [email@gmail.com] ja cadastrado");


        verify(dao, never()).salvar(any());

    }

    @Test
    void irAtualizarUmPacienteComEmailNovo() {

        Long id = 1L;

        PacienteRequest pacienteAtualizado = PacienteRequest.builder()
                .nome("Teste 1")
                .sexo(Sexo.M)
                .email("email@gmail.com")
                .dataNascimento(LocalDate.now())
                .build();

        Paciente salvado = new Paciente(1L, "Teste 1", "teste@gmail.com", LocalDate.now(), Sexo.M);

        when(dao.buscar(id)).thenReturn(Optional.of(salvado));
        when(dao.existePorEmail(pacienteAtualizado.getEmail())).thenReturn(false);
        when(dao.salvar(any())).thenReturn(salvado);

        underTest.atualizar(id, pacienteAtualizado);

        ArgumentCaptor<Paciente> argumentCaptor = ArgumentCaptor.forClass(Paciente.class);
        verify(dao).salvar(argumentCaptor.capture());

        Paciente atualizado = argumentCaptor.getValue();

        assertThat(atualizado.getNome()).isEqualTo(pacienteAtualizado.getNome());
        assertThat(atualizado.getEmail()).isEqualTo(pacienteAtualizado.getEmail());
        assertThat(atualizado.getDataNascimento()).isEqualTo(pacienteAtualizado.getDataNascimento());
        assertThat(atualizado.getSexo()).isEqualTo(pacienteAtualizado.getSexo());

    }

    @Test
    void irAtualizarUmPacienteComNomeNovo() {

        Long id = 1L;

        PacienteRequest pacienteAtualizado = PacienteRequest.builder()
                .nome("Teste Atualizado")
                .sexo(Sexo.M)
                .email("teste@gmail.com")
                .dataNascimento(LocalDate.now())
                .build();

        Paciente salvado = new Paciente(1L, "Teste 1", "teste@gmail.com", LocalDate.now(), Sexo.M);

        when(dao.buscar(id)).thenReturn(Optional.of(salvado));
        when(dao.salvar(any())).thenReturn(salvado);

        underTest.atualizar(id, pacienteAtualizado);

        ArgumentCaptor<Paciente> argumentCaptor = ArgumentCaptor.forClass(Paciente.class);
        verify(dao).salvar(argumentCaptor.capture());

        Paciente atualizado = argumentCaptor.getValue();

        assertThat(atualizado.getNome()).isEqualTo(pacienteAtualizado.getNome());
        assertThat(atualizado.getEmail()).isEqualTo(pacienteAtualizado.getEmail());
        assertThat(atualizado.getDataNascimento()).isEqualTo(pacienteAtualizado.getDataNascimento());
        assertThat(atualizado.getSexo()).isEqualTo(pacienteAtualizado.getSexo());

    }

    @Test
    void irLancarUmaExcecaoAoAtualizarUmPacientePorNaoEncontrarPaciente() {

        Long id = 1L;

        PacienteRequest pacienteAtualizado = PacienteRequest.builder()
                .nome("Teste Atualizado")
                .sexo(Sexo.M)
                .email("email@gmail.com")
                .dataNascimento(LocalDate.now())
                .build();

        when(dao.buscar(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.atualizar(id, pacienteAtualizado))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Paciente [1] nao encontrado");

        verify(dao, never()).salvar(any());


    }

    @Test
    void irLancarUmaExcecaoAoAtualizarUmPacientePorEmailJaCadastrado() {

        Long id = 1L;

        PacienteRequest pacienteAtualizado = PacienteRequest.builder()
                .nome("Teste Atualizado")
                .sexo(Sexo.M)
                .email("email@gmail.com")
                .dataNascimento(LocalDate.now())
                .build();

        Paciente salvado = new Paciente(1L, "Teste 1", "teste@gmail.com", LocalDate.now(), Sexo.M);

        when(dao.buscar(id)).thenReturn(Optional.of(salvado));

        when(dao.existePorEmail(pacienteAtualizado.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> underTest.atualizar(id, pacienteAtualizado))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email [email@gmail.com] ja cadastrado");

        verify(dao, never()).salvar(any());


    }
}