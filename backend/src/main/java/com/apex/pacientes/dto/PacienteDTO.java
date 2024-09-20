package com.apex.pacientes.dto;

import com.apex.pacientes.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteDTO {

    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Sexo sexo;

}
