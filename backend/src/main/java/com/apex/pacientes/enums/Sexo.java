package com.apex.pacientes.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sexo {

    M("Masculino"), F("Feminino");

    private final String descricao;

}
