package com.connection.connection.repository;

import org.neo4j.driver.*;

public class Neo4jRelacionamento implements AutoCloseable {

    private final Driver driver;

    public Neo4jRelacionamento() {
        driver = GraphDatabase.driver("bolt://localhost:7687",
                AuthTokens.basic("neo4j", "senha"));
    }

    public void criarPessoa(Long id, String nome) {
        try (Session session = driver.session()) {
            session.run("MERGE (p:Pessoa {id: $id, nome: $nome})",
                    Values.parameters("id", id, "nome", nome));
        }
    }

    public void criarRelacaoAmizade(Long id1, Long id2) {
        try (Session session = driver.session()) {
            session.run(
                    "MATCH (a:Pessoa {id: $id1}), (b:Pessoa {id: $id2}) " +
                    "MERGE (a)-[:AMIGO_DE]->(b)",
                    Values.parameters("id1", id1, "id2", id2));
        }
    }

    public void listarAmigos(Long id) {
        try (Session session = driver.session()) {
            var result = session.run(
                    "MATCH (p:Pessoa {id: $id})-[:AMIGO_DE]->(amigos) RETURN amigos.nome",
                    Values.parameters("id", id));
            System.out.println("Amigos da pessoa id " + id + ":");
            while (result.hasNext()) {
                var record = result.next();
                System.out.println("- " + record.get("amigos.nome").asString());
            }
        }
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}