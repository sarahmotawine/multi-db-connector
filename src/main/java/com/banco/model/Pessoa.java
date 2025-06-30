package com.banco.model;


public class Pessoa {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String dataNascimento;

    public Pessoa(Long id, String nome, String email, String cpf, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

     // Getters e Setters
     public Long getId() { return id; }
     public String getNome() { return nome; }
     public String getEmail() { return email; }
     public String getCpf() { return cpf; }
     public String getDataNascimento() { return dataNascimento; }
 
     public void setId(Long id) { this.id = id; }
     public void setNome(String nome) { this.nome = nome; }
     public void setEmail(String email) { this.email = email; }
     public void setCpf(String cpf) { this.cpf = cpf; }
     public void setIdade(String dataNascimento) { this.dataNascimento = dataNascimento; }
 
     @Override
     public String toString() {
         return "Pessoa{id=%d, nome='%s', email='%s', cpf='%s', dataNascimento='%s'}".formatted(id, nome, email, cpf, dataNascimento);
     }
 }