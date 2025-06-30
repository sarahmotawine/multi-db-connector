package com.banco.repository;

import com.banco.database.ConexaoRedis;
import com.banco.model.Pessoa;

import redis.clients.jedis.Jedis;

public class PessoaRedisCache {

    // Salva a pessoa por ID e também cria um índice para busca por CPF
    public void salvar(Pessoa pessoa) {
        try (Jedis jedis = ConexaoRedis.getConnection()) {
            String idKey = "pessoa:" + pessoa.getId();
            String cpfKey = "cpf:" + pessoa.getCpf();

            String valor = String.join(";",
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getCpf(),
                pessoa.getDataNascimento()
            );

            // Salva os dados da pessoa
            jedis.set(idKey, valor);

            // Mapeia CPF para ID
            jedis.set(cpfKey, pessoa.getId().toString());

        } catch (Exception e) {
            System.err.println("Erro ao salvar no Redis: " + e.getMessage());
        }
    }

    // Busca uma pessoa pelo ID
    public Pessoa buscar(Long id) {
        try (Jedis jedis = ConexaoRedis.getConnection()) {
            String chave = "pessoa:" + id;
            String valor = jedis.get(chave);

            if (valor != null) {
                String[] partes = valor.split(";");
                if (partes.length >= 4) {
                    String nome = partes[0];
                    String email = partes[1];
                    String cpf = partes[2];
                    String nascimento = partes[3];
                    return new Pessoa(id, nome, email, cpf, nascimento);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar no Redis: " + e.getMessage());
        }
        return null;
    }

    // Busca uma pessoa pelo CPF
    public Pessoa buscarPorCpf(String cpf) {
        try (Jedis jedis = ConexaoRedis.getConnection()) {
            String cpfKey = "cpf:" + cpf;
            String idString = jedis.get(cpfKey);

            if (idString != null) {
                Long id = Long.parseLong(idString);
                return buscar(id);
            } else {
                System.out.println("CPF não encontrado no Redis: " + cpf);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar por CPF no Redis: " + e.getMessage());
        }
        return null;
    }
}