package BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionBD {
	private static final String url = "jdbc:postgresql://localhost:5432/TrabalhoFinalPOO";
	private static final String usuario = "postgres";
	private static final String senha = "Jgmm2006.";
	private static Connection connection;

	public static Connection getConnection() {
		System.out.println("Conectando no banco de dados...");

		try {
			connection = DriverManager.getConnection(url, usuario, senha);
			if (connection != null) {
				System.out.println("Conectado com sucesso!\n");
				criarSchemaETabelas();
			} else {
				System.out.println("Erro nos dados da conexão!");
			}
		} catch (SQLException e) {
			System.err.println("Não foi possível conectar...");
			e.printStackTrace();
		}

		return connection;
	}

	private static void criarSchemaETabelas() {
		String sql = """
				CREATE SCHEMA IF NOT EXISTS ProjetoFinal;

				DO $$
				BEGIN
				    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'parentesco_enum') THEN
				        CREATE TYPE ProjetoFinal.Parentesco_Enum AS ENUM ('FILHO', 'SOBRINHO', 'OUTROS');
				    END IF;
				END$$;

				CREATE TABLE IF NOT EXISTS ProjetoFinal.Funcionario (
				    id SERIAL PRIMARY KEY,
				    nome TEXT NOT NULL,
				    cpf VARCHAR(11) UNIQUE NOT NULL,
				    data_nascimento DATE NOT NULL,
				    salario_bruto NUMERIC(15,2) NOT NULL DEFAULT 0
				);

				CREATE TABLE IF NOT EXISTS ProjetoFinal.Dependentes (
				    id SERIAL PRIMARY KEY,
				    nome TEXT NOT NULL,
				    cpf VARCHAR(11) UNIQUE NOT NULL,
				    data_nascimento DATE NOT NULL,
				    parentesco ProjetoFinal.Parentesco_Enum NOT NULL,
				    funcionario_id INTEGER NOT NULL REFERENCES ProjetoFinal.Funcionario(id) ON DELETE CASCADE
				);

				CREATE TABLE IF NOT EXISTS ProjetoFinal.FolhaPagamento (
				    id SERIAL PRIMARY KEY,
				    funcionario INTEGER NOT NULL REFERENCES ProjetoFinal.Funcionario(id) ON DELETE CASCADE,
				    data_pagamento DATE NOT NULL,
				    desconto_inss NUMERIC(15,2) NOT NULL,
				    desconto_ir NUMERIC(15,2) NOT NULL,
				    salario_liquido NUMERIC(15,2) NOT NULL
				);
				""";

		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(sql);
			System.out.println("Schema e tabelas verificados/criados com sucesso!\n");
		} catch (SQLException e) {
			System.err.println("Erro ao criar schema/tabelas!");
			e.printStackTrace();
		}
	}
}
