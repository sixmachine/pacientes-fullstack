package com.apex.pacientes.model;

import com.apex.pacientes.enums.Sexo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "paciente",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paciente {

    @Id
    @SequenceGenerator(
            name = "paciente_id_seq",
            sequenceName = "paciente_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "paciente_id_seq"
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String nome;
    @Column(
            nullable = false
    )
    private String email;
    @Column(
            nullable = false
    )
    private LocalDate dataNascimento;

    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public Paciente(final String nome,
                    final String email,
                    final LocalDate dataNascimento,
                    final Sexo sexo) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }
}
