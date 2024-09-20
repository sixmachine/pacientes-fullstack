package com.apex.pacientes.repository;

import com.apex.pacientes.AbstractTestcontainers;
import com.apex.pacientes.enums.Sexo;
import com.apex.pacientes.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PacienteRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private PacienteRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void existsByEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Paciente paciente = new Paciente(
                FAKER.name().fullName(),
                email,
                LocalDate.now(),
                Sexo.M);

        underTest.save(paciente);

        // When
        var actual = underTest.existsByEmail(email);

        // Then
        assertThat(actual).isTrue();
    }


    @Test
    void existsById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Paciente paciente = new Paciente(
                FAKER.name().fullName(),
                email,
                LocalDate.now(),
                Sexo.M);

        Paciente saved = underTest.save(paciente);

        // When
        var actual = underTest.existsById(saved.getId());

        // Then
        assertThat(actual).isTrue();
    }
}