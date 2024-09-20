package com.apex.pacientes.controller;

import com.apex.pacientes.dto.PacienteDTO;
import com.apex.pacientes.request.PacienteRequest;
import com.apex.pacientes.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pacientes")
public class PacienteController {

    public final PacienteService service;

    @GetMapping
    public Page<PacienteDTO> buscarTodos(@PageableDefault final Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("{id}")
    public PacienteDTO buscarPorId(@PathVariable("id") final Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("{id}")
    public void remover(@PathVariable final Long id) {
        service.excluir(id);
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> adicionar(@RequestBody final PacienteRequest request) {
        return new ResponseEntity<>(service.adicionar(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable("id") final Long id,
                                                 @RequestBody final PacienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

}
