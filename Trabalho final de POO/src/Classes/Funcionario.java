package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoa {

	private int id;
	private double salarioBruto = 0;
	private double descontoINSS = 0;
	private double descontoIR = 0;
	private List<Dependentes> dependentes = new ArrayList<Dependentes>();

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto,
			List<Dependentes> dependentes) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.dependentes = new ArrayList<>();
	}

	public Funcionario(String nome, String cpf, LocalDate dataNascimento) {
		super(nome, cpf, dataNascimento);
	}

	public void adicionarDependente(Dependentes d) {
		dependentes.add(d);
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getDescontoINSS() {
		return descontoINSS;
	}

	public void setDescontoINSS(double descontoINSS) {
		this.descontoINSS = descontoINSS;
	}

	public void setDescontoIR(double descontoIR) {
		this.descontoIR = descontoIR;
	}

	public double getDescontoIR() {
		return descontoIR;
	}

	public List<Dependentes> getDependentes() {
		return dependentes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String imprimirDados() {
		return "--------------------------\n" + "\nNome: " + getNome() + "\nCpf: " + getCpf() + "\nData de nascimento: "
				+ getDataNascimento();
	}

}
