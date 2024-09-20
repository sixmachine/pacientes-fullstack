package com.apex.pacientes.mapper;

import com.apex.pacientes.dto.PacienteDTO;
import com.apex.pacientes.model.Paciente;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PacienteMapper implements Function<Paciente, PacienteDTO> {

    @Override
    public PacienteDTO apply(final Paciente paciente) {
        return PacienteDTO.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .email(paciente.getEmail())
                .dataNascimento(paciente.getDataNascimento())
                .sexo(paciente.getSexo())
                .build();
    }
}
