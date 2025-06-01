package com.connection.connection.controller;

import com.connection.connection.model.Pessoa;
import com.connection.connection.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public void salvar(@RequestBody Pessoa pessoa) {
        pessoaService.salvar(pessoa);
    }

    @GetMapping("/{cpf}")
    public Pessoa buscarPorCpf(@PathVariable String cpf) {
        return pessoaService.buscarPorCpf(cpf);
    }

    @GetMapping
    public List<Pessoa> listarTodas() {
        return pessoaService.listarTodas();
    }

    @PutMapping
    public void atualizar(@RequestBody Pessoa pessoa) {
        pessoaService.atualizar(pessoa);
    }

    @DeleteMapping("/{cpf}")
    public void deletar(@PathVariable String cpf) {
        pessoaService.deletarPorCpf(cpf);
    }
}