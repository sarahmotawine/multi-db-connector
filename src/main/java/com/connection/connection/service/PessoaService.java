package com.connection.connection.service;

import com.connection.connection.model.Pessoa;
import java.util.List;

public interface PessoaService {
    Pessoa salvar(Pessoa pessoa);
    Pessoa buscarPorCpf(String cpf);
    List<Pessoa> listarTodas();
    Pessoa atualizar(Pessoa pessoa);
    void deletarPorCpf(String cpf);
}