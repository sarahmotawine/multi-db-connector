package com.banco.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.banco.database.ConexaoPostgres;
import com.banco.model.Pessoa;

public class PessoaPostgresRepository {

    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (id, nome, email, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pessoa.getId());
            stmt.setString(2, pessoa.getNome());
            stmt.setString(3, pessoa.getEmail());
            stmt.setString(4, pessoa.getCpf());
            stmt.setString(5, pessoa.getDataNascimento()); 
            stmt.executeUpdate();

            System.out.println("Pessoa salva com sucesso!");
        } catch (SQLException e) {
            if (e.getMessage().contains("violates unique constraint")) {
                System.err.println("Erro: já existe uma pessoa com o ID " + pessoa.getId());
            } else {
                System.err.println("Erro ao salvar pessoa: " + e.getMessage());
            }
        }
    }

    public void salvarOuAtualizar(Pessoa pessoa) {
        Pessoa existente = buscarPorId(pessoa.getId());
        if (existente == null) {
            salvar(pessoa);
        } else {
            atualizar(pessoa);
        }
    }

    public Pessoa buscarPorId(Long id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pessoa(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("cpf"),
                    rs.getString("data_nascimento")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa: " + e.getMessage());
        }
        return null;
    }

    public List<Pessoa> listarTodas() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("cpf"),
                    rs.getString("data_nascimento")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pessoas: " + e.getMessage());
        }

        return pessoas;
    }

    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, email = ?, cpf = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setString(3, pessoa.getCpf());
            stmt.setString(4, pessoa.getDataNascimento());
            stmt.setLong(5, pessoa.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Pessoa atualizada com sucesso!");
            } else {
                System.out.println("Pessoa com ID %d não encontrada.".formatted(pessoa.getId()));
            }

        } catch (Exception e) {
            System.err.println("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Pessoa deletada com sucesso!");
            } else {
                System.out.println("Pessoa com ID %d não encontrada.".formatted(id));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar pessoa: " + e.getMessage());
        }
    }
}
