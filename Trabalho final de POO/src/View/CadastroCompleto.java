package View;

import Classes.Dependentes;
import Classes.Funcionario;
import Enum.Parentesco;
import Serviço.CalculoSalarioLiquido;
import Classes.FolhaPagamento;
import dao.FuncionarioDao;
import dao.DependenteDao;
import dao.FolhaPagamentoDao;
import Expection.DependenteExpection;
import resource.Util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CadastroCompleto {

	public static void cadastrarFuncionario() throws DependenteExpection {
		String nome = Util.informarString("Nome do funcionário: ");
		String cpf = Util.informarString("CPF (somente números): ");
		LocalDate dataNascimento = LocalDate.parse(Util.informarString("Data de nascimento (yyyy-MM-dd): "));
		double salarioBruto = Util.informarDouble("Salário bruto: ");

		Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, salarioBruto, new ArrayList<>());

		FuncionarioDao.inserir(funcionario);

		char resp = Util.informarChar("Deseja adicionar dependentes? (S/N): ");
		while (Character.toUpperCase(resp) == 'S') {
			String nomeDep = Util.informarString("Nome do dependente: ");
			String cpfDep = Util.informarString("CPF do dependente: ");
			LocalDate dataDep = LocalDate.parse(Util.informarString("Data de nascimento (yyyy-MM-dd): "));
			String parentescoStr = Util.informarString("Parentesco (FILHO/SOBRINHO/OUTROS): ");
			Parentesco parentesco = Parentesco.valueOf(parentescoStr.toUpperCase());

			Dependentes dep = new Dependentes(nomeDep, cpfDep, dataDep, parentesco);
			funcionario.adicionarDependente(dep);

			DependenteDao.inserir(dep, funcionario.getId());

			resp = Util.informarChar("Deseja adicionar outro dependente? (S/N): ");
		}

		FolhaPagamento folha = CalculoSalarioLiquido.calcularSalario(funcionario, 1);

		FolhaPagamentoDao.inserir(folha);

		System.out.println("\n=== FUNCIONÁRIO CADASTRADO ===");
		System.out.println("Nome: " + funcionario.getNome());
		System.out.println("CPF: " + funcionario.getCpf());
		System.out.println("Data de nascimento: " + funcionario.getDataNascimento());
		System.out.println("Salário bruto: " + funcionario.getSalarioBruto());
		System.out.println("Desconto INSS: " + folha.getDescontoINSS());
		System.out.println("Desconto IR: " + folha.getDescontoIR());
		System.out.println("Salário líquido: " + folha.getSalarioLiquido());

		System.out.println("\n--- DEPENDENTES ---");
		for (Dependentes d : funcionario.getDependentes()) {
			System.out.println("Nome: " + d.getNome() + ", CPF: " + d.getCpf() + ", Nascimento: "
					+ d.getDataNascimento() + ", Parentesco: " + d.getParentesco());
		}
	}
}
