package BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	    try (Statement stmt = connection.createStatement()) {

	        
	        stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS ProjetoFinal;");

	        
	        try (ResultSet rs = stmt.executeQuery(
	        	    "SELECT 1 FROM pg_type WHERE typname = 'parentesco_enum';"
	        	)) {
	        	    if (!rs.next()) {
	        	        stmt.executeUpdate(
	        	            "CREATE TYPE ProjetoFinal.parentesco_enum AS ENUM ('FILHO', 'SOBRINHO', 'OUTROS');"
	        	        );
	        	    }
	        	}

	    
	        stmt.executeUpdate(
	            "CREATE TABLE IF NOT EXISTS ProjetoFinal.Funcionario (" +
	            "id SERIAL PRIMARY KEY," +
	            "nome TEXT NOT NULL," +
	            "cpf VARCHAR(11) UNIQUE NOT NULL," +
	            "data_nascimento DATE NOT NULL," +
	            "salario_bruto NUMERIC(15,2) NOT NULL" +
	            ");"
	        );

	       
	        stmt.executeUpdate(
	            "CREATE TABLE IF NOT EXISTS ProjetoFinal.Dependentes (" +
	            "id SERIAL PRIMARY KEY," +
	            "parentesco ProjetoFinal.parentesco_enum NOT NULL," +
	            "nome TEXT NOT NULL," +
	            "cpf VARCHAR(11) UNIQUE NOT NULL," +
	            "data_nascimento DATE NOT NULL," +
	            "cod_funcionario INTEGER REFERENCES ProjetoFinal.Funcionario(id) ON DELETE CASCADE" +
	            ");"
	        );

	        
	        stmt.executeUpdate(
	            "CREATE TABLE IF NOT EXISTS ProjetoFinal.FolhaPagamento (" +
	            "id SERIAL PRIMARY KEY," +
	            "funcionario INTEGER NOT NULL REFERENCES ProjetoFinal.Funcionario(id) ON DELETE CASCADE," +
	            "data_pagamento DATE NOT NULL," +
	            "desconto_inss NUMERIC(15,2) NOT NULL," +
	            "desconto_ir NUMERIC(15,2) NOT NULL," +
	            "salario_liquido NUMERIC(15,2) NOT NULL" +
	            ");"
	        );

	        System.out.println("Schema e tabelas criadas/verificadas com sucesso!\n");

	    } catch (SQLException e) {
	        System.err.println("Erro ao criar schema/tabelas!");
	        e.printStackTrace();
	    }
	}
}
