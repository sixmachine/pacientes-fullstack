package com.apex.pacientes.request;

import com.apex.pacientes.enums.Sexo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteRequest {

    @NotEmpty
    private String nome;
    @NotEmpty
    private String email;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    private Sexo sexo;

}
