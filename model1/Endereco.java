package model1;

import java.util.Scanner;

public class Endereco {

	private String lougradouro;
	private String numero;
	private String complemento;
	private String cidade;
	private String estado;
	
	public Endereco(){}
	
	public Endereco(String lougradouro, String numero, String complemento, String cidade, String estado) {
		this.lougradouro = lougradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
		this.estado = estado;
	}

	public Endereco(String lougradouro, String numero, String cidade, String estado) {
		this.lougradouro = lougradouro;
		this.numero = numero;
		this.complemento = "";
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public void registrar(Scanner scan){

		do {

			System.out.print("\nLougradouro: ");
			this.lougradouro = scan.nextLine();

		} while (this.lougradouro.isBlank());

		do {

			System.out.print("Número: ");
			this.numero = scan.nextLine();

		} while (this.numero.isBlank());


		System.out.print("Complemento: ");
		this.complemento = scan.nextLine();

		do {

			System.out.print("Cidade: ");
			this.cidade = scan.nextLine();

		} while (this.cidade.isBlank());

		do {

			System.out.print("Estado: ");
			this.estado = scan.nextLine();

		} while (this.estado.isBlank());

	}

	public String getLougradouro() {
		return lougradouro;
	}
	
	public void setLougradouro(String lougradouro) {
		this.lougradouro = lougradouro;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
