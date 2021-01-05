# Sistema de votação

## Resumo
O sistema de votação criado para este desafio foi idealizado como foco na flexibilidade e simplicidade, permitindo que se adapte à possiveis diferentes tipos de votações em um futuro hipotético.

- O arquivo para criação da estrutura de dados encontra-se na raíz do repositório (voting-system-db.sql). Deve ser atribuido à variável de ambiente "MYSQL_HOST" o host em que o banco de dados será criado.
- As possibilidades de votos são dinâmicas (VoteType) e precisam ser previamente cadastradas.
- Os votos são permitidos apenas à associados previamente cadastrados com um CPF válido e aptos à votação (ABLE TO VOTE).

## Tecnologias utilizadas
Para a construção da API foram utilizados utilizados: Java 8, Spring Framework e banco de dados MySQL. Os testes unitários utilizam Junit e Banco de dados H2 em memória.

## O que faltou

Infelizmente, devido a outros compromissos, não consegui tempo hábil para entregar todas as funcionalidades que tinha em mente, focando em desenvolver as features essenciais. Sendo assim, não consegui utilizar
algumas tecnologias e práticas que estou habituado a utilizar no dia-a-dia, sendo elas:

- Versionamento do banco de dados utilizando liquibase.
- Documentação da API utilizando Swagger.
- Teste unitários mais precisos.
- Documentação do código.
