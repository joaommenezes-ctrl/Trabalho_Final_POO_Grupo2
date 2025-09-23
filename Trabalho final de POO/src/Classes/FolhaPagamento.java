package Classes;

import java.time.LocalDate;

public class FolhaPagamento {

	private int id;
	private Funcionario funcionario;
	private LocalDate dataPagamento;
	private double descontoINSS = 0;
	private double descontoIR = 0;
	private double salarioLiquido;

	public FolhaPagamento(int id, Funcionario funcionario, LocalDate dataPagamento, double descontoINSS,
			double descontoIR, double salarioLiquido) {
		super();
		this.id = id;
		this.funcionario = funcionario;
		this.dataPagamento = dataPagamento;
		this.descontoINSS = descontoINSS;
		this.descontoIR = descontoIR;
		this.salarioLiquido = salarioLiquido;
	}

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public double getDescontoINSS() {
		return descontoINSS;
	}

	public void setDescontoINSS(double descontoINSS) {
		this.descontoINSS = descontoINSS;
	}

	public double getDescontoIR() {
		return descontoIR;
	}

	public void setDescontoIR(double descontoIR) {
		this.descontoIR = descontoIR;
	}

	public double getSalarioLiquido() {
		return salarioLiquido;
	}

	public void setSalarioLiquido(double salarioLiquido) {
		this.salarioLiquido = salarioLiquido;
	}

	@Override
	public String toString() {
		return "FolhaPagamento [codigo=" + id + ", funcionario=" + funcionario + ", dataPagamento=" + dataPagamento
				+ ", descontoINSS=" + descontoINSS + ", descontoIR=" + descontoIR + ", salarioLiquido=" + salarioLiquido
				+ "]";
	}

	// String format(%.2f, descontoIR)
}
