
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BancoDeDados.ConnectionBD;
import Classes.Dependentes;
import Classes.FolhaPagamento;
import Classes.Funcionario;
import Enum.Parentesco;
import Expection.DependenteExpection;
import Serviço.CalculoSalarioLiquido;

public class FuncionarioDao {
	private static Connection connection;

	public FuncionarioDao() {
		this.connection = new ConnectionBD().getConnection();
	}

	public static void inserir(Funcionario funcionario) {
		String sql = "INSERT INTO ProjetoFinal.Funcionario (nome, cpf, data_nascimento, salario_bruto) VALUES (?, ?, ?, ?) RETURNING id";

		try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				funcionario.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizar(Funcionario funcionario) {
		String sql = "UPDATE ProjetoFinal.Funcionario SET nome = ?, cpf = ?, data_nascimento = ?, salario_bruto = ? WHERE id = ?";

		try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());
			stmt.setInt(5, funcionario.getId());
			System.out.println("ID do funcionário para atualização: " + funcionario.getId());
			int rows = stmt.executeUpdate();
			if (rows == 0) {
				System.out.println("Nenhum funcionário encontrado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remover(String cpf) {

		String deleteDependentes = "DELETE FROM ProjetoFinal.Dependentes WHERE cod_funcionario = (SELECT id FROM ProjetoFinal.Funcionario WHERE cpf = ?)";
		String deleteFolha = "DELETE FROM ProjetoFinal.FolhaPagamento WHERE funcionario = (SELECT id FROM ProjetoFinal.Funcionario WHERE cpf = ?)";
		String deleteFuncionario = "DELETE FROM ProjetoFinal.Funcionario WHERE cpf = ?";

		try (Connection conn = ConnectionBD.getConnection()) {

			try (PreparedStatement stmt = conn.prepareStatement(deleteDependentes)) {
				stmt.setString(1, cpf);
				stmt.executeUpdate();
			}

			try (PreparedStatement stmt = conn.prepareStatement(deleteFolha)) {
				stmt.setString(1, cpf);
				stmt.executeUpdate();
			}

			try (PreparedStatement stmt = conn.prepareStatement(deleteFuncionario)) {
				stmt.setString(1, cpf);
				int rows = stmt.executeUpdate();
				if (rows > 0) {
					System.out.println("Funcionário excluído com sucesso!");
				} else {
					System.out.println("Funcionário não encontrado.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Funcionario> listar() throws DependenteExpection {
		List<Funcionario> funcionarios = new ArrayList<>();
		String sqlFunc = "SELECT * FROM ProjetoFinal.Funcionario";

		try (Connection conn = ConnectionBD.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rsFunc = stmt.executeQuery(sqlFunc)) {

			while (rsFunc.next()) {
				int id = rsFunc.getInt("id");
				String nome = rsFunc.getString("nome");
				String cpf = rsFunc.getString("cpf");
				LocalDate data = rsFunc.getDate("data_nascimento").toLocalDate();
				double salarioBruto = rsFunc.getDouble("salario_bruto");

				Funcionario f = new Funcionario(nome, cpf, data, salarioBruto, new ArrayList<>());

				String sqlDeps = "SELECT * FROM ProjetoFinal.Dependentes WHERE cod_funcionario = ?";
				try (PreparedStatement stmtDeps = conn.prepareStatement(sqlDeps)) {
					stmtDeps.setInt(1, id);
					ResultSet rsDeps = stmtDeps.executeQuery();
					while (rsDeps.next()) {
						String nomeDep = rsDeps.getString("nome");
						String cpfDep = rsDeps.getString("cpf");
						LocalDate dataDep = rsDeps.getDate("data_nascimento").toLocalDate();
						String parentesco = rsDeps.getString("parentesco");
						Dependentes d = new Dependentes(nomeDep, cpfDep, dataDep, Parentesco.valueOf(parentesco));
						f.adicionarDependente(d);
					}
				}

				funcionarios.add(f);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return funcionarios;
	}

	public int obterIdPorCpf(String cpf) {
		String sql = "SELECT id FROM ProjetoFinal.Funcionario WHERE cpf = ?";
		try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Funcionario obterFuncionarioPorId(int id) {
		String sql = "SELECT * FROM ProjetoFinal.Funcionario WHERE id = ?";
		try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Funcionario f = new Funcionario(rs.getString("nome"), rs.getString("cpf"),
							rs.getDate("data de nascimento").toLocalDate());
					f.setId(rs.getInt("id"));
					return f;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
