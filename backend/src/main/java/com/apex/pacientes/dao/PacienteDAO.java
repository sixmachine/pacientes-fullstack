package com.apex.pacientes.dao;

import com.apex.pacientes.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PacienteDAO {

    Paciente salvar(final Paciente paciente);

    void excluir(final Long id);

    Optional<Paciente> buscar(final Long id);

    Page<Paciente> listar(final Pageable pageable);

    Boolean existePorEmail(final String email);

    Boolean existePorId(final Long id);

}
