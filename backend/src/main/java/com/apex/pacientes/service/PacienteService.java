package com.apex.pacientes.service;

import com.apex.pacientes.dao.PacienteDAO;
import com.apex.pacientes.dto.PacienteDTO;
import com.apex.pacientes.exception.DuplicateResourceException;
import com.apex.pacientes.exception.ResourceNotFoundException;
import com.apex.pacientes.mapper.PacienteMapper;
import com.apex.pacientes.model.Paciente;
import com.apex.pacientes.request.PacienteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteDAO dao;
    private final PacienteMapper mapper;


    public PacienteDTO buscarPorId(final Long id) {
        return dao.buscar(id)
                .map(mapper)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente [%s] nao encontrado".formatted(id)));
    }

    public Page<PacienteDTO> listar(final Pageable pageable) {
        return dao.listar(pageable)
                .map(mapper);

    }

    public void excluir(final Long id) {
        existeId(id);
        dao.excluir(id);
    }


    public PacienteDTO adicionar(final PacienteRequest novoPaciente) {

        verificaEmailCadastrado(novoPaciente.getEmail());
        final Paciente paciente = Paciente.builder()
                .nome(novoPaciente.getNome())
                .dataNascimento(novoPaciente.getDataNascimento())
                .email(novoPaciente.getEmail())
                .sexo(novoPaciente.getSexo())
                .build();

        return mapper.apply(dao.salvar(paciente));

    }

    public PacienteDTO atualizar(final Long id,
                          final PacienteRequest pacienteAtualizado) {

        final Paciente paciente = dao.buscar(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente [%s] nao encontrado".formatted(id)));

        if (!paciente.getEmail().equals(pacienteAtualizado.getEmail())) {
            verificaEmailCadastrado(pacienteAtualizado.getEmail());
        }

        paciente.setNome(pacienteAtualizado.getNome());
        paciente.setSexo(pacienteAtualizado.getSexo());
        paciente.setEmail(pacienteAtualizado.getEmail());
        paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());

        return mapper.apply(dao.salvar(paciente));

    }


    private void verificaEmailCadastrado(final String email) {
        if (dao.existePorEmail(email)) {
            throw new DuplicateResourceException("Email [%s] ja cadastrado".formatted(email));
        }
    }

    private void existeId(final Long id) {
        if (!dao.existePorId(id)) {
            throw new ResourceNotFoundException("Paciente [%s] nao encontrado".formatted(id));
        }
    }

}
