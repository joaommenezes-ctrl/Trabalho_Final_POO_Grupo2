package BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
				System.out.println("Conectado com sucesso!");
			} else {
				System.out.println("Erro nos dados da conexão!");
			}
		} catch (SQLException e) {
			System.err.println("Não foi possível conectar...");
			e.printStackTrace();
		}

		return connection;
	}
}
