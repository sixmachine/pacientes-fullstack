CREATE TABLE paciente
(
    id              BIGSERIAL PRIMARY KEY,
    nome            TEXT NOT NULL,
    email           TEXT NOT NULL,
    sexo            TEXT NOT NULL,
    data_nascimento DATE  NOT NULL
);