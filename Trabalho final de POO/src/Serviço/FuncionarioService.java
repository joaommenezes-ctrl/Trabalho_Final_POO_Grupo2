package Serviço;

import java.util.List;

import Classes.Funcionario;
import Expection.DependenteExpection;

public interface FuncionarioService {
	void cadastrarFuncionario() throws DependenteExpection;

	List<Funcionario> listarFuncionarios() throws DependenteExpection;

	void atualizarFuncionario() throws DependenteExpection;

	void excluirFuncionario() throws DependenteExpection;

	void imprimirFuncionario() throws DependenteExpection;

	void importarArquivo() throws DependenteExpection;

}
