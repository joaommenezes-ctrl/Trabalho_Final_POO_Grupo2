package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BancoDeDados.ConnectionBD;
import Classes.FolhaPagamento;

public class FolhaPagamentoDao {

	public static void inserir(FolhaPagamento folha) {
		String sql = "INSERT INTO ProjetoFinal.FolhaPagamento (funcionario, data_pagamento, desconto_inss, desconto_ir, salario_liquido) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = new ConnectionBD().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, folha.getFuncionario().getId());
			stmt.setDate(2, Date.valueOf(folha.getDataPagamento()));
			stmt.setDouble(3, folha.getDescontoINSS());
			stmt.setDouble(4, folha.getDescontoIR());
			stmt.setDouble(5, folha.getSalarioLiquido());

			stmt.executeUpdate();
			System.out.println("Folha de pagamento inserida com sucesso!");
		} catch (SQLException e) {
			System.err.println("Erro ao inserir folha de pagamento: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
