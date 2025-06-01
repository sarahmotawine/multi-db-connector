package com.connection.connection.repository;

import com.connection.connection.model.Pessoa;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

public class RedisCache {

    private Jedis jedis;
    private Gson gson;

    public RedisCache() {
        this.jedis = new Jedis("localhost", 6379);
        this.gson = new Gson();
    }

    public void cachePessoa(Pessoa pessoa) {
        String json = gson.toJson(pessoa);
        jedis.setex("pessoa:" + pessoa.getCpf(), 3600, json); // cache por 1 hora
    }

    public Pessoa getPessoaByCpf(String cpf) {
        String json = jedis.get("pessoa:" + cpf);
        if (json != null) {
            return gson.fromJson(json, Pessoa.class);
        }
        return null;
    }

    public void invalidateCache(String cpf) {
        jedis.del("pessoa:" + cpf);
    }
}