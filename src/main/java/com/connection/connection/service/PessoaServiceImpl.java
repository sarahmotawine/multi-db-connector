package com.connection.connection.service;

import com.connection.connection.model.Pessoa;
import com.connection.connection.repository.PessoaPostgresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaPostgresRepository pessoaRepository;

    public PessoaServiceImpl(PessoaPostgresRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        try {
            return pessoaRepository.create(pessoa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pessoa", e);
        }
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) {
        try {
            return pessoaRepository.readByCpf(cpf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pessoa por CPF", e);
        }
    }

    @Override
    public List<Pessoa> listarTodas() {
        try {
            return pessoaRepository.readAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pessoas", e);
        }
    }

    @Override
    public Pessoa atualizar(Pessoa pessoa) {
        try {
            pessoaRepository.update(pessoa);
            return pessoa;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar pessoa", e);
        }
    }

    @Override
    public void deletarPorCpf(String cpf) {
        try {
            Pessoa pessoa = buscarPorCpf(cpf);
            if (pessoa != null) {
                pessoaRepository.delete(pessoa.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar pessoa", e);
        }
    }
}