package com.banco;

import com.banco.create_tabela.CriarTabelasPostgres;
import com.banco.model.Pessoa;
import com.banco.repository.PessoaNeo4jGraph;
import com.banco.services.PessoaService;

public class Main {
    public static void main(String[] args) {
        PessoaService service = new PessoaService();
        PessoaNeo4jGraph grafo = new PessoaNeo4jGraph();
        CriarTabelasPostgres create = new CriarTabelasPostgres();
        create.criarTabelaPessoa();

        // Criar e salvar duas pessoas no sistema (PostgreSQL + Redis + Mongo)
        Pessoa p1 = new Pessoa(1L, "João", "JyL9w@example.com", "123.456.789-00", "1990-01-01");
        Pessoa p2 = new Pessoa(2L, "Maria", "naria@ex.com", "987.654.321-00", "1995-05-05");

        service.salvarPessoa(p1);
        service.salvarPessoa(p2);

        // Registrar amizade entre elas no Neo4j
        grafo.criarAmizade(p1.getId(), p1.getNome(), p2.getId(), p2.getNome());
    }
}