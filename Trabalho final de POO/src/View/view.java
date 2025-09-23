package View;

import java.util.ArrayList;
import java.util.Scanner;

import Arquivo.GeradorCSV;
import Expection.DependenteExpection;
import Serviço.FuncionarioService;
import Serviço.FuncionarioServiceImpl;

public class view {
	static final String HEADER = "|    SISTEMA DE CADASTRO   |";
	static final String LINHA = "----------------------------";
	private static FuncionarioService service = new FuncionarioServiceImpl();
	private static Scanner scanner = new Scanner(System.in);

	public static void menu() {
		linha();
		header();
		linha();
		System.out.println("""
				1 - Cadastrar
				2 - Alterar
				3 - Excluir
				4 - Imprimir
				5 - Importar e Exportar arquivo CSV
				6 - Sair
				""");
	}

	public static int selecionarMenu() {
		return resource.Util.informarInt("Selecione uma opção: ");
	}

	public static void selecionarOpcao(int opcao) {
		try {
			switch (opcao) {
			case 1 -> service.cadastrarFuncionario();
			case 2 -> service.atualizarFuncionario();
			case 3 -> service.excluirFuncionario();
			case 4 -> service.imprimirFuncionario();
			case 5 -> {
				FuncionarioServiceImpl fs = new FuncionarioServiceImpl();
				try {
					fs.importarArquivo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			case 6 -> System.out.println("Saindo...");
			default -> System.out.println("Opção inválida.");
			}
		} catch (DependenteExpection e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro inesperado: " + e.getMessage());
		}
	}

	public static void header() {
		System.out.println(HEADER);
	}

	public static void linha() {
		System.out.println(LINHA);
	}
}
