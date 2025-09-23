package Serviço;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Arquivo.GeradorCSV;
import Classes.Funcionario;
import Enum.Parentesco;
import dao.*;
import resource.Util;
import Expection.DependenteExpection;
import View.CadastroCompleto;
import Serviço.CalculoSalarioLiquido;
import Classes.Dependentes;
import Classes.FolhaPagamento;

public class FuncionarioServiceImpl implements FuncionarioService {

	private FuncionarioDao funcionarioDao = new FuncionarioDao();

	@Override
	public void cadastrarFuncionario() throws DependenteExpection {
		CadastroCompleto.cadastrarFuncionario();
	}

	@Override
	public List<Funcionario> listarFuncionarios() throws DependenteExpection {
		return funcionarioDao.listar();
	}

	@Override
	public void atualizarFuncionario() throws DependenteExpection {
		List<Funcionario> lista = funcionarioDao.listar();
		String cpf = Util.informarString("CPF do funcionário a alterar: ");
		Funcionario encontrado = null;
		for (Funcionario f : lista) {
			if (f.getCpf().equals(cpf)) {
				encontrado = f;
				break;
			}
		}

		if (encontrado != null) {
			int id = funcionarioDao.obterIdPorCpf(cpf);
			if (id == 0) {
				System.out.println("Erro: funcionário não encontrado no banco!");
				return;
			}
			encontrado.setId(id);

			String nome = Util.informarString("Novo nome: ");
			String data = Util.informarString("Nova data de nascimento (yyyy-MM-dd): ");
			double salario = Util.informarDouble("Novo salário bruto: ");

			encontrado.setNome(nome);
			encontrado.setDataNascimento(java.time.LocalDate.parse(data));
			encontrado.setSalarioBruto(salario);

			funcionarioDao.atualizar(encontrado);
			System.out.println("Funcionário alterado com sucesso!");
		} else {
			System.out.println("Funcionário não encontrado.\n");
		}
	}

	@Override
	public void excluirFuncionario() throws DependenteExpection {
		List<Funcionario> lista = funcionarioDao.listar();
		String cpf = Util.informarString("CPF do funcionário a excluir: ");
		boolean encontrado = false;
		for (Funcionario f : lista) {
			if (f.getCpf().equals(cpf)) {
				funcionarioDao.remover(cpf);
				encontrado = true;
				break;
			}
		}
		if (!encontrado) {
			System.out.println("Funcionário não encontrado.");
		}
	}

	@Override
	public void imprimirFuncionario() throws DependenteExpection {

		List<Funcionario> lista = new ArrayList<>();
		try {
			lista = funcionarioDao.listar();
		} catch (DependenteExpection e) {
			e.printStackTrace();
		}
		if (lista.isEmpty()) {
			System.out.println("Nenhum funcionário cadastrado.");
			return;
		}

		String cpf = Util.informarString("Informe o CPF do funcionário para imprimir: ");
		Funcionario f = null;
		for (Funcionario func : lista) {
			if (func.getCpf().equals(cpf)) {
				f = func;
				break;
			}
		}

		if (f == null) {
			System.out.println("Funcionário não encontrado.");
			return;
		}

		FolhaPagamento folha = CalculoSalarioLiquido.calcularSalario(f, f.getDependentes().size());
		System.out.println("Nome: " + f.getNome() + ", CPF: " + f.getCpf() + ", Nascimento: " + f.getDataNascimento()
				+ ", Desconto INSS: " + String.format("%.2f", folha.getDescontoINSS()) + ", Desconto IR: "
				+ String.format("%.2f", folha.getDescontoIR()) + ", Salário Líquido: "
				+ String.format("%.2f", folha.getSalarioLiquido()));

	}

	public void importarArquivo() throws DependenteExpection {
		String caminhoEntrada = Util.informarString("Informe o caminho do arquivo TXT/CSV: ");

		FuncionarioDao funcionarioDao = new FuncionarioDao();
		DependenteDao dependenteDao = new DependenteDao();
		List<FolhaPagamento> folhas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				if (linha.trim().isEmpty())
					continue;

				String[] partes = linha.split(";", -1);
				if (partes.length < 4)
					continue;

				
				Funcionario funcionario = new Funcionario(partes[0], 
						partes[1], 
						LocalDate.parse(partes[2]), 
						Double.parseDouble(partes[3]),
						new ArrayList<>() 
				);

				funcionarioDao.inserir(funcionario);
				int idGerado = funcionarioDao.obterIdPorCpf(funcionario.getCpf());
				funcionario.setId(idGerado);

			
				for (int i = 4; i + 3 < partes.length; i += 4) {
					String nomeDep = partes[i];
					String cpfDep = partes[i + 1];
					String dataNascDep = partes[i + 2];
					Parentesco parentesco = Parentesco.valueOf(partes[i + 3].toUpperCase());

					if (!nomeDep.isBlank() && !cpfDep.isBlank()) {
						Dependentes dependente = new Dependentes(nomeDep, cpfDep, LocalDate.parse(dataNascDep),
								parentesco);

						dependenteDao.inserir(dependente, funcionario.getId());

						funcionario.getDependentes().add(dependente);
					}
				}

			
				FolhaPagamento folha = CalculoSalarioLiquido.calcularSalario(funcionario, funcionario.getId());
				FolhaPagamentoDao.inserir(folha);
				folhas.add(folha);
			}

			System.out.println("Importação concluída! Funcionários importados: " + folhas.size());

		} catch (IOException e) {
			System.out.println("Erro ao importar arquivo: " + e.getMessage());
			return;
		}

		String caminhoSaida = Util.informarString(
				"Informe o caminho completo do CSV de saída (ex: C:\\Users\\joaog\\Desktop\\saida.csv): ");

		GeradorCSV.gerarCSV(folhas, caminhoSaida);

		System.out.println("CSV gerado com sucesso em: " + caminhoSaida);
	}

}
