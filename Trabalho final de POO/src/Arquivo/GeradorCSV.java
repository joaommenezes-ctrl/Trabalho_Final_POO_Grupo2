package Arquivo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Classes.Funcionario;
import Classes.Dependentes;
import Classes.FolhaPagamento;
import Serviço.CalculoSalarioLiquido;
import dao.FuncionarioDao;

public class GeradorCSV {

	public static void gerarCSV(List<FolhaPagamento> folhas, String caminhoArquivo) {
		CalculoSalarioLiquido calculoSalario = new CalculoSalarioLiquido();

		try (FileWriter writer = new FileWriter(caminhoArquivo)) {

			writer.append("Nome;CPF;DataNascimento;SalarioBruto;DescontoINSS;DescontoIR;SalarioLiquido;Parentesco\n");

			for (FolhaPagamento f : folhas) {
				double descontoINSS = calculoSalario.calcularINSS(f.getFuncionario().getSalarioBruto());
				double descontoIR = calculoSalario.calcularIR(f.getFuncionario().getSalarioBruto() - descontoINSS);
				double salarioLiquido = f.getFuncionario().getSalarioBruto() - descontoINSS - descontoIR;

				writer.append("FUNCIONARIO;" + f.getFuncionario().getNome() + ";" + f.getFuncionario().getCpf() + ";"
						+ f.getFuncionario().getDataNascimento() + ";" + f.getFuncionario().getSalarioBruto() + ";"
						+ descontoINSS + ";" + descontoIR + ";" + salarioLiquido + ";\n");

				for (Dependentes d : f.getFuncionario().getDependentes()) {
					writer.append("DEPENDENTE;" + d.getNome() + ";" + d.getCpf() + ";" + d.getDataNascimento() + ";;;;;"
							+ d.getParentesco() + "\n\n");
				}
			}

			System.out.println("CSV gerado com sucesso em: " + caminhoArquivo);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
