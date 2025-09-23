package Serviço;

import Classes.Funcionario;

import java.time.LocalDate;

import Classes.Dependentes;
import Classes.FolhaPagamento;

public class CalculoSalarioLiquido {

	public static FolhaPagamento calcularSalario(Funcionario f, int codigo) {

		double salarioBruto = f.getSalarioBruto();

		double descontoInss = calcularINSS(salarioBruto);
		double dependentes = f.getDependentes().size() * Dependentes.getAbatimento();  //size pega quantos dependentes tem
																						 
		double baseIR = salarioBruto - descontoInss - dependentes;
		double descontoIR = calcularIR(baseIR);

		f.setDescontoINSS(descontoInss);
		f.setDescontoIR(descontoIR);
		double salarioLiquido = salarioBruto - descontoInss - descontoIR;

		return new FolhaPagamento(codigo, f, LocalDate.now(), descontoInss, descontoIR, salarioLiquido);

	}

	public static double calcularINSS(double salario) {
		double inss;

		if (salario <= 1518.00) {
			inss = (salario * 0.075);
		} else if (salario <= 2793.88) {
			inss = (salario * 0.09) - 22.77;
		} else if (salario <= 4190.83) {
			inss = (salario * 0.12) - 106.60;
		} else if (salario <= 8157.41) {
			inss = (salario * 0.14) - 190.42;
		} else {
			inss = 951.62;
		}

		return inss;
	}

	public static double calcularIR(double base) {
		if (base <= 2259.20)
			return 0.0;
		else if (base <= 2826.25)
			return (base * 0.075) - 169.44;
		else if (base <= 3751.05)
			return (base * 0.15) - 381.44;
		else if (base <= 4664.68)
			return (base * 0.225) - 662.77;
		else
			return (base * 0.275) - 896.00;
	}
}
