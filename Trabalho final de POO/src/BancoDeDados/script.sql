CREATE SCHEMA ProjetoFinal;

-- Pessoa abstrata -> representada por funcionario e dependente
-- Então não precisa de tabela "Pessoa"

CREATE TABLE ProjetoFinal.Funcionario (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    salario_bruto NUMERIC(15,2) NOT NULL
);

CREATE TYPE ProjetoFinal.Parentesco_Enum AS ENUM (
    'FILHO', 'SOBRINHO', 'OUTROS'
);

CREATE TABLE ProjetoFinal.Dependente (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    parentesco ProjetoFinal.Parentesco_Enum NOT NULL,
    funcionario_id INTEGER NOT NULL REFERENCES ProjetoFinal.Funcionario(id)
);

CREATE TABLE ProjetoFinal.FolhaPagamento (
    id SERIAL PRIMARY KEY,
    funcionario INTEGER NOT NULL REFERENCES ProjetoFinal.Funcionario(id),
    data_pagamento DATE NOT NULL,
    desconto_inss NUMERIC(15,2) NOT NULL,
    desconto_ir NUMERIC(15,2) NOT NULL,
    salario_liquido NUMERIC(15,2) NOT NULL
);

ALTER TABLE ProjetoFinal.Funcionario
ADD COLUMN desconto_inss NUMERIC(15,2) NOT NULL DEFAULT 0,
ADD COLUMN desconto_ir NUMERIC(15,2) NOT NULL DEFAULT 0;

ALTER TABLE ProjetoFinal.Funcionario
ADD COLUMN salario_liquido NUMERIC(15,2) NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS ProjetoFinal.Dependentes(
    id SERIAL PRIMARY KEY,
    parentesco ProjetoFinal.Parentesco_Enum NOT NULL,
    nome TEXT NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    cod_funcionario INTEGER REFERENCES ProjetoFinal.Funcionario(id)
);

ALTER TABLE ProjetoFinal.Funcionario
    ADD COLUMN salario_bruto NUMERIC(15,2) NOT NULL DEFAULT 0;

SELECT * FROM ProjetoFinal.Funcionario;

SELECT * FROM ProjetoFinal.Dependentes;

ALTER TABLE ProjetoFinal.FolhaPagamento
DROP CONSTRAINT folhapagamento_funcionario_fkey,
ADD CONSTRAINT folhapagamento_funcionario_fkey
FOREIGN KEY (funcionario)
REFERENCES ProjetoFinal.Funcionario(id)
ON DELETE CASCADE;

ALTER TABLE ProjetoFinal.Dependente
DROP CONSTRAINT dependente_funcionario_id_fkey,
ADD CONSTRAINT dependente_funcionario_id_fkey
FOREIGN KEY (funcionario_id)
REFERENCES ProjetoFinal.Funcionario(id)
ON DELETE CASCADE;
