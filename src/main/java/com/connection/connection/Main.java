package com.connection.connection;

import com.connection.connection.model.Pessoa;
import com.connection.connection.service.PessoaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        PessoaService pessoaService = context.getBean(PessoaService.class);

        // Criar e salvar pessoas
        Pessoa pessoa1 = new Pessoa(3L, "João Silva", "joao@email.com", "12345678902", "26.03.2003");
        Pessoa pessoa2 = new Pessoa(4L, "Maria Souza", "maria@email.com", "98765432106", "04.04.1967");
        
        pessoaService.salvar(pessoa1);
        pessoaService.salvar(pessoa2);

        // Buscar pessoa
        Pessoa encontrada = pessoaService.buscarPorCpf("12345678901");
        System.out.println("Pessoa encontrada: " + encontrada.getNome());
    }
}