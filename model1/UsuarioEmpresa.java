package model1;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UsuarioEmpresa extends Usuario{

	private String grupo;
	private String cnpj;


	public UsuarioEmpresa(){}

	public UsuarioEmpresa(String nome, String email, String senha, String cnpj, Endereco endereco, Conta conta) {
		super(nome, email, senha, endereco, conta);
		this.cnpj = cnpj;
	}

	@Override
	public void registrar(List<Usuario> listaUsuarios, Scanner scan) {

		do {

			System.out.print("\nDigite seu nome: ");
			this.setNome(scan.nextLine());

		} while (!validaNome(this.getNome()));

		do {

			System.out.print("Digite sua senha: ");
			this.setSenha(scan.nextLine());

		} while (!validaSenha(this.getSenha()));

		do {
			System.out.print("Digite seu cnpj: ");
			this.cnpj = scan.nextLine();

		} while (!validaCnpj(this.cnpj, listaUsuarios));

		do {

			System.out.print("\nDigite seu email: ");
			this.setEmail(scan.nextLine());

		} while (!validaEmail(this.getEmail()));


		this.setEndereco(new Endereco());
		this.getEndereco().registrar(scan);
		this.setConta(new Conta());
		this.getConta().registrar(scan);

		listaUsuarios.add(this);

	}

	public static boolean validaCnpj(String cnpj, List<Usuario> listaUsuarios){

		cnpj = cnpj.replace('-', ' ').replace('.', ' ').replace('/', ' ').replaceAll("\\s+", "");

		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
				cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
				cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
				cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
				cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
				(cnpj.length() != 14)) {

			return(false);

		}

		for (Usuario usuario : listaUsuarios){

			if (usuario.getClass() == UsuarioEmpresa.class){

				if (((UsuarioEmpresa) usuario).getCnpj() == cnpj){

					System.out.println("Cnpj já cadastrado no Sistema!");
					return false;

				}

			}

		}

		char digito13, digito14;

		try {

			// Calculo do Primeiro Digito Verificador

			int soma = 0;
			int peso = 2;

			for (int i = 11; i >= 0; i--) {

				int num = (int)(cnpj.charAt(i) - 48);

				soma = soma + (num * peso);
				peso = peso + 1;

				if (peso == 10) {
					peso = 2;
				}

			}

			int resultado = soma % 11;

			if ((resultado == 0) || (resultado == 1)) {

				digito13 = '0';

			} else{

				digito13 = (char) ((11-resultado) + 48);

			}

			// Calculo do Segundo Digito Verificador

			soma = 0;
			peso = 2;
			for (int i = 12; i >= 0; i--) {

				int num = (int)(cnpj.charAt(i)- 48);

				soma = soma + (num * peso);
				peso = peso + 1;

				if (peso == 10) {
					peso = 2;
				}
			}

			resultado = soma % 11;

			if ((resultado == 0) || (resultado == 1)) {

				digito14 = '0';
			}  else {

				digito14 = (char)((11-resultado) + 48);

			}

			// Verifica se os dígitos calculados conferem com os dígitos informados.

			if ((digito13 == cnpj.charAt(12)) && (digito14 == cnpj.charAt(13))){

				return true;

			} else {
				return false;
			}

		} catch (InputMismatchException erro) {
			return false;
		}

	}

	public static String formataCNPJ(String cnpj) {

		return(cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
				cnpj.substring(5, 8) + "." + cnpj.substring(8, 12) + "-" +
				cnpj.substring(12, 14));
	}

	public String getGrupo() {
		return this.grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
