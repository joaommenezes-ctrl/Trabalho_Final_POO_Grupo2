# Residentes: 
- Nathan Cristino;
- Gabriel Speridião dos Santos;
- João Gabriel Menezes Marra;
- Claudiane Sutil;
- Lucas Falco;




# Projeto Final - Programação Orientada a Objetos

Este projeto foi desenvolvido como trabalho final da disciplina de **Programação Orientada a Objetos**. O objetivo é consolidar e demonstrar a aplicação dos principais conceitos de POO vistos ao longo do curso, por meio da criação de um sistema para cálculo do salário líquido de funcionários de uma empresa, considerando descontos e abatimentos de dependentes.

## Objetivo

O programa realiza o cálculo do salário líquido de um ou mais funcionários, considerando descontos de INSS, IR e abatimentos conforme a legislação vigente para dependentes. Para isso, foi necessário implementar uma estrutura completa de classes, interfaces, enumeradores e tratamento de exceções, promovendo o uso dos conceitos fundamentais de orientação a objetos.

## Requisitos e Conceitos Utilizados

O projeto contempla os seguintes conceitos e recursos:

- **Interfaces**
- **Classes Abstratas**
- **Classes Concretas**
- **Encapsulamento**
- **Modificadores de Acesso**
- **Exceções (incluindo exceção personalizada para dependentes)**
- **Enums**
- **Trabalho com Arquivos (entrada e saída)**
- **Datas utilizando `LocalDate`**
- **Coleções (HashSet, ArrayList ou Map)**
- **Herança**
- **Organização em Pacotes**

## Estrutura de Classes

O sistema possui as seguintes classes obrigatórias:

- **Pessoa** (classe abstrata)
  - `nome`, `cpf`, `dataNascimento`
- **Funcionário** (herda de Pessoa)
  - `salarioBruto`, `descontoInss`, `descontoIR`
  - Não permite funcionários com CPF repetido.
- **Dependente** (herda de Pessoa)
  - `parentesco`
  - Os tipos de parentesco permitidos são: `FILHO`, `SOBRINHO` e `OUTROS` (utilizando Enum)
  - Cada dependente proporciona um abatimento de R$189,59 no IR, conforme tabela.
  - Todos os dependentes devem ser menores de 18 anos e não podem ter CPFs repetidos.

Além disso, foi criada uma exceção personalizada (`DependenteException`) para tratar regras específicas dos dependentes.

## Organização em Pacotes

O código está separado em pacotes, promovendo uma melhor organização e separação de responsabilidades.

## Entrada e Saída

O programa solicita ao usuário, via console ou arquivo, os nomes dos arquivos de entrada e saída para processar os dados dos funcionários e dependentes.

## Diagrama UML

O projeto inclui o diagrama de classes UML para melhor visualização da arquitetura desenvolvida.


<img width="1095" height="675" alt="Diagrama_Poo_2" src="https://github.com/user-attachments/assets/d6de2451-461f-4513-a955-f0492ff45c90" />
