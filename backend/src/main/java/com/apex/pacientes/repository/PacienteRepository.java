package com.apex.pacientes.repository;

import com.apex.pacientes.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Boolean existsByEmail(final String email);

}
