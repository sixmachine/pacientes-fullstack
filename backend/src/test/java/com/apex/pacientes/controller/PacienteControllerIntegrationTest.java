package com.apex.pacientes.controller;

import com.apex.pacientes.dto.PacienteDTO;
import com.apex.pacientes.enums.Sexo;
import com.apex.pacientes.request.PacienteRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PacienteControllerIntegrationTest {


    @Autowired
    private WebTestClient webTestClient;
    private static final String PACIENTE_PATH = "/api/v1/pacientes";

    @BeforeEach
    void setUp() {
    }

    @Test
    void buscarTodos() {

        webTestClient.get()
                .uri(PACIENTE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.content").isNotEmpty();

    }

    @Test
    void buscarPorId() {

        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";

        PacienteRequest request = new PacienteRequest(
                name, email, LocalDate.now(), Sexo.M
        );

        PacienteDTO salvo = webTestClient.post()
                .uri(PACIENTE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), PacienteRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();

        PacienteDTO encontrado = webTestClient.get()
                .uri(PACIENTE_PATH + "/{id}", salvo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(salvo).isEqualTo(encontrado);

    }

    @Test
    void remover() {

        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";

        PacienteRequest request = new PacienteRequest(
                name, email, LocalDate.now(), Sexo.M
        );

        PacienteDTO salvo = webTestClient.post()
                .uri(PACIENTE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), PacienteRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();

        webTestClient.delete()
                .uri(PACIENTE_PATH + "/{id}", salvo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();


    }

    @Test
    void adicionar() {

        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";

        PacienteRequest request = new PacienteRequest(
                name, email, LocalDate.now(), Sexo.M
        );

        PacienteDTO salvo = webTestClient.post()
                .uri(PACIENTE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), PacienteRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(salvo.getNome()).isEqualTo(request.getNome());
        assertThat(salvo.getEmail()).isEqualTo(request.getEmail());
        assertThat(salvo.getDataNascimento()).isEqualTo(request.getDataNascimento());
        assertThat(salvo.getSexo()).isEqualTo(request.getSexo());

    }

    @Test
    void atualizar() {

        Faker faker = new Faker();
        Name fakerName = faker.name();

        String name = fakerName.fullName();
        String email = fakerName.lastName() + "-" + UUID.randomUUID() + "@amigoscode.com";

        PacienteRequest request = new PacienteRequest(
                name, email, LocalDate.now(), Sexo.M
        );

        PacienteDTO salvo = webTestClient.post()
                .uri(PACIENTE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), PacienteRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();

        request.setNome("Atulizado");

        PacienteDTO atualizado = webTestClient.put()
                .uri(PACIENTE_PATH + "/{id}",salvo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), PacienteRequest.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<PacienteDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(atualizado.getNome()).isEqualTo(request.getNome());

    }
}