package Classes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import Enum.*;
import Expection.DependenteExpection;

public class Dependentes extends Pessoa {

	private static final double ABATIMENTO = 189.59;
	private static Set<String> cpfRegistrados = new HashSet<>();
	private Parentesco parentesco;

	public Dependentes(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco)
			throws DependenteExpection {
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;

		if (dataNascimento.isBefore(LocalDate.now().minusYears(18))) {
			throw new DependenteExpection("Dependente precisa ter menos de 18 anos.");
		}

		if (cpfRegistrados.contains(cpf)) {
			throw new DependenteExpection("Já existe um Dependente com este CPF: " + cpf);
		}
	}


	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public static double getAbatimento() {
		return ABATIMENTO;
	}
}