# multi-db-connector

Projeto demonstrativo que integra múltiplos bancos de dados (PostgreSQL, Redis, MongoDB e Neo4j) para CRUD da entidade Pessoa em Java.

---

## Objetivo

Este projeto tem como finalidade mostrar a integração de diferentes tecnologias de banco de dados em um sistema único, exemplificando a aplicação prática dos conceitos estudados no curso de Sistemas de Informação.

---

## Tecnologias utilizadas

- **Java 17**
- **PostgreSQL:** armazenamento principal dos dados da Pessoa.
- **Redis:** cache para otimizar consultas por CPF.
- **MongoDB:** armazenamento dos logs das operações (audit logs).
- **Neo4j:** gerenciamento de relacionamentos entre pessoas (amizades).
- **Maven:** gerenciamento de dependências e build.
- **SLF4J + Logback:** para logging.

---

## Estrutura do projeto

- `model` — Classe Pessoa.
- `repository` — Implementações para acesso aos bancos.
- `services` — Lógica de negócio para CRUD e cache.
- `database` — Configurações e conexões.
- `Main` — Classe principal que demonstra o funcionamento.

---

## Funcionalidades

- CRUD completo da entidade Pessoa (id, nome, email, cpf, dataNascimento).
- Logs de cada operação são gravados no MongoDB.
- Cache de dados em Redis para buscas rápidas por CPF.
- Registro e consulta de relacionamentos de amizade no Neo4j.

---

## Como executar

1. Configure os bancos de dados (PostgreSQL, Redis, MongoDB e Neo4j) e ajuste as strings de conexão nas classes do pacote `database`.
2. Clone o repositório:

```bash
git clone https://github.com/sarahmotawine/multi-db-connector.git
cd multi-db-connector