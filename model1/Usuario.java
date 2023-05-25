package model1;

import utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
	private String nome;
	private String senha;
	private String email;
	private Endereco endereco;
	private Conta conta;
	private String identificador;
	private LocalDateTime dataRegistro = LocalDateTime.now();
	private double totalAlimentosDoado;
	private double totalDinheiroDoado;
	private int reducaoImposto;
	private final int REDUCAO_IMPOSTO_KG = 13;
	private final int REDUCAO_IMPOSTO_RS = 780;
	private final int REDUCAO_IMPOSTO = 390;
	private final int LIMITE_REDUCAO = 2000;

	public Usuario(){}

	public Usuario(String nome, String email, String senha, Endereco endereco) {

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.endereco = endereco;
	}

	public Usuario(String nome, String email, String senha, Conta conta) {

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.conta = conta;
	}

	public Usuario(String nome,String email, String senha, Endereco endereco, Conta conta) {

		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.endereco = endereco;
		this.conta = conta;
	}

	public void registrar(List<Usuario> listaUsuarios ,Scanner scan){}

	public boolean validaNome(String nome) {

		if (nome.isBlank()) {

			System.out.println("\nNome não pode estar vazio!");

			return false;

		}

		if (!nome.substring(0).matches("[A-Za-z]*")) {

			System.out.println("\nNome deve conter apenas letras!");

			return false;

		}

		return true;

	}

	public boolean validaSenha(String senha) {

		if (senha.length() < 8){

			System.out.println("\nA senha deve conter pelo menos 8 caracteres!");
			return false;

		}

		return true;

	}

	public boolean validaEmail(String email) {

		if (email != null && email.length() > 0) {

		}
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {

			System.out.println("Email inválido!");

			return false;
		}

		return true;

	}

	public static Usuario validaLogin(String email, String senha, List<Usuario> listaUsuarios, List<Instituicao> listaInstituicoes){

		List<Usuario> listaUsuariosInstituicoes = new ArrayList<>();

		listaUsuariosInstituicoes.addAll(listaInstituicoes);
		listaUsuariosInstituicoes.addAll(listaUsuarios);

		for (Usuario usuario : listaUsuariosInstituicoes){

			if (usuario.email.equals(email)){

				if (usuario.senha.equals(senha)){

					return usuario;

				}

			}

		}

		System.out.println("Login Inválido!");

		return null;


	}

	public int checarReducaoImposto(){

		int quantidadeReducaoRS = Math.floorDiv((int)Math.floor(totalDinheiroDoado), REDUCAO_IMPOSTO_RS);
		int quantidadeReducaoKG = Math.floorDiv((int)Math.floor(totalAlimentosDoado), REDUCAO_IMPOSTO_KG);

		int reducaoTotal = (quantidadeReducaoRS + quantidadeReducaoKG) * REDUCAO_IMPOSTO;

		if (reducaoTotal > LIMITE_REDUCAO){

			this.reducaoImposto = 2000;

		} else {

			this.reducaoImposto = reducaoTotal;

		}

		return this.reducaoImposto;
	}

	public void verInformacoes(Scanner scan){

		Utils.decoraTexto("Informações do Usuário");

		System.out.println("Nome: " + this.nome);
		System.out.println("Email: " + this.email);
		System.out.println("Data do Registro: " + this.dataRegistro);
		System.out.println("\nENDEREÇO:\nLougradouro: " + this.endereco.getLougradouro());
		System.out.println("Número: " + this.endereco.getNumero());
		System.out.println("Complemento: " + this.endereco.getComplemento());
		System.out.println("Cidade: " + this.endereco.getCidade());
		System.out.println("Estado: " + this.endereco.getEstado());
		System.out.println("\nCONTA:\nTitular: " + this.conta.getTitular());
		System.out.println("Número: " + this.conta.getNumero());
		System.out.println("Saldo: " + this.conta.getSaldo());

		System.out.print("\nPressione enter para continuar...");

		String descartavel = scan.nextLine();

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Conta getConta() {
		return this.conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public LocalDateTime getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDateTime dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public double getTotalAlimentosDoado() {
		return totalAlimentosDoado;
	}

	public void setTotalAlimentosDoado(double totalAlimentosDoado) {
		this.totalAlimentosDoado = totalAlimentosDoado;
	}

	public double getTotalDinheiroDoado() {
		return totalDinheiroDoado;
	}

	public void setTotalDinheiroDoado(double totalDinheiroDoado) {
		this.totalDinheiroDoado = totalDinheiroDoado;
	}

	public int getReducaoImposto() {
		return reducaoImposto;
	}

	public void setReducaoImposto(int reducaoImposto) {
		this.reducaoImposto = reducaoImposto;
	}

}