package Aplicação;

import View.view;
import dao.FuncionarioDao;
import Expection.DependenteExpection;
import Serviço.FuncionarioServiceImpl;

public class Main {
	public static void main(String[] args) throws DependenteExpection {
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		int opcao;
		do {
			view.menu();
			opcao = view.selecionarMenu();
			view.selecionarOpcao(opcao);

			if (opcao == 5) {
				FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl();

			}
		} while (opcao != 6);

	}
}
