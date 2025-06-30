package com.banco.services;

import com.banco.model.Pessoa;
import com.banco.repository.PessoaMongoLogger;
import com.banco.repository.PessoaPostgresRepository;
import com.banco.repository.PessoaRedisCache;

public class PessoaService {

    private PessoaPostgresRepository postgres = new PessoaPostgresRepository();
    private PessoaRedisCache redis = new PessoaRedisCache();
    private PessoaMongoLogger mongo = new PessoaMongoLogger();

    public void salvarPessoa(Pessoa pessoa) {
        postgres.salvarOuAtualizar(pessoa);
        redis.salvar(pessoa);
        mongo.log("UPSERT", pessoa); // novo ou atualizado
    }

    public Pessoa buscarPessoa(Long id) {
        Pessoa pessoa = redis.buscar(id);
        if (pessoa != null) {
            System.out.println("Pessoa encontrada no Redis");
            return pessoa;
        }

        pessoa = postgres.buscarPorId(id);
        if (pessoa != null) {
            System.out.println("Pessoa encontrada no PostgreSQL, salvando no Redis...");
            redis.salvar(pessoa);
        }

        return pessoa;
    }

    public void deletarPessoa(Long id) {
        Pessoa pessoa = postgres.buscarPorId(id);
        if (pessoa != null) {
            postgres.deletar(id);
            redis.salvar(pessoa); // opcional: remover do Redis também
            mongo.log("DELETE", pessoa);
        } else {
            System.out.println("Pessoa com ID %d não encontrada.".formatted(id));
        }
    }
}
