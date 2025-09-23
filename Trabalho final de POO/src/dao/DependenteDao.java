package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BancoDeDados.ConnectionBD;
import Classes.Dependentes;

public class DependenteDao {

	public static void inserir(Dependentes dep, int idFuncionario) {
		String sql = "INSERT INTO ProjetoFinal.Dependentes "
				+ "(nome, cpf, data_nascimento, parentesco, cod_funcionario) "
				+ "VALUES (?, ?, ?, ?::projetofinal.parentesco_enum, ?)";
		try (Connection conn = new ConnectionBD().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, dep.getNome());
			stmt.setString(2, dep.getCpf());
			stmt.setDate(3, Date.valueOf(dep.getDataNascimento()));
			stmt.setString(4, dep.getParentesco().name());
			stmt.setInt(5, idFuncionario);

			stmt.executeUpdate();
			System.out.println("Dependente " + dep.getNome() + " inserido com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao inserir dependente: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
