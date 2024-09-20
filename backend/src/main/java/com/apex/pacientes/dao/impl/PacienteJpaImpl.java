package com.apex.pacientes.dao.impl;

import com.apex.pacientes.dao.PacienteDAO;
import com.apex.pacientes.model.Paciente;
import com.apex.pacientes.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PacienteJpaImpl implements PacienteDAO {


    private final PacienteRepository repository;

    @Override
    public Paciente salvar(final Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    public void excluir(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Paciente> buscar(final Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Paciente> listar(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Boolean existePorEmail(final String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean existePorId(final Long id) {
        return repository.existsById(id);
    }
}
