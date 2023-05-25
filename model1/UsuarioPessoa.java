package model1;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UsuarioPessoa extends Usuario{

	private String cpf;

	public UsuarioPessoa(){}

	public UsuarioPessoa(String nome, String email, String senha, String cpf, Endereco endereco, Conta conta) {
		super(nome, email, senha, endereco, conta);
		this.cpf = cpf;
	}

	public static boolean validaCpf(String cpf, List<Usuario> listaUsuarios){

		cpf = cpf.replace('-', ' ').replace('.', ' ').replaceAll("\\s+", "");

		if (cpf.equals("00000000000") || cpf.equals("11111111111")||
				cpf.equals("22222222222") || cpf.equals("33333333333") ||
				cpf.equals("44444444444") || cpf.equals("55555555555") ||
				cpf.equals("66666666666") || cpf.equals("77777777777") ||
				cpf.equals("88888888888") || cpf.equals("99999999999") ||
				(cpf.length() != 11)){

			return (false);

		}

		for (Usuario usuario : listaUsuarios){

			if (usuario.getClass() == UsuarioPessoa.class){

				if (((UsuarioPessoa) usuario).getCpf() == cpf){

					System.out.println("Cpf já cadastrado no Sistema!");
					return false;

				}

			}

		}

		char digito10, digito11;

		try {

			// Calculo do Primeiro Digito Verificador
			int soma = 0;
			int peso = 10;
			for (int i = 0; i < 9; i++) {

				int num = (int) (cpf.charAt(i) - 48);
				soma = soma + (num * peso);
				peso = peso - 1;

			}

			int resultado = 11 - (soma % 11);

			if ((resultado == 10) || (resultado == 11)) {
				digito10 = '0';
			} else {
				digito10 = (char) (resultado + 48);
			}

			// Calculo do Segundo Digito Verificador

			soma = 0;
			peso = 11;
			for (int i = 0; i < 10; i++) {
				int num = (int) (cpf.charAt(i) - 48);
				soma = soma + (num * peso);
				peso = peso - 1;
			}

			resultado = 11 - (soma % 11);
			if ((resultado == 10) || (resultado == 11))
				digito11 = '0';
			else
				digito11 = (char) (resultado + 48);

			if ((digito10 == cpf.charAt(9)) && (digito11 == cpf.charAt(10))) {
				return true;
			} else {
				return false;
			}
		} catch (InputMismatchException erro) {

			return false;

		}

	}

	public static String formataCpf(String cpf){

		return(cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
				cpf.substring(6, 9) + "-" + cpf.substring(9, 11));

	}

	@Override
	public void registrar(List<Usuario> listaUsuarios, Scanner scan){


		do {

			System.out.print("\nDigite seu nome: ");
			this.setNome(scan.nextLine());

		} while (!validaNome(this.getNome()));

		do {

			System.out.print("Digite sua senha: ");
			this.setSenha(scan.nextLine());

		} while (!validaSenha(this.getSenha()));

		do{
			System.out.print("Digite seu Cpf: ");
			this.cpf = scan.nextLine();

		} while(!validaCpf(this.cpf, listaUsuarios));

		do {

			System.out.print("Digite seu email: ");
			this.setEmail(scan.nextLine());

		} while (!validaEmail(this.getEmail()));


		this.setEndereco(new Endereco());
		this.getEndereco().registrar(scan);
		this.setConta(new Conta());
		this.getConta().registrar(scan);
		listaUsuarios.add(this);


	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
