package com.connection.connection.repository;

import com.connection.connection.model.Pessoa;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PessoaPostgresRepository {

    private final String url = "jdbc:postgresql://localhost:5432/connection_db";
    private final String user = "admin";
    private final String password = "adminadmin";

    public PessoaPostgresRepository() {
        try (Connection conn = getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS pessoa (" +
                         "id SERIAL PRIMARY KEY," +
                         "nome VARCHAR(100)," +
                         "email VARCHAR(100)," +
                         "cpf VARCHAR(20) UNIQUE," +
                         "data_nascimento VARCHAR(20))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Pessoa create(Pessoa p) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, email, cpf, data_nascimento) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getCpf());
            ps.setString(4, p.getDataNascimento());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getLong(1));
            }
        }
        return p;
    }

    public Pessoa readById(Long id) throws SQLException {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToPessoa(rs);
            }
        }
        return null;
    }

    public Pessoa readByCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM pessoa WHERE cpf = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToPessoa(rs);
            }
        }
        return null;
    }

    public List<Pessoa> readAll() throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pessoas.add(mapRowToPessoa(rs));
            }
        }
        return pessoas;
    }

    public void update(Pessoa p) throws SQLException {
        String sql = "UPDATE pessoa SET nome=?, email=?, cpf=?, data_nascimento=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getCpf());
            ps.setString(4, p.getDataNascimento());
            ps.setLong(5, p.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Pessoa mapRowToPessoa(ResultSet rs) throws SQLException {
        return new Pessoa(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("cpf"),
            rs.getString("data_nascimento")
        );
    }
}