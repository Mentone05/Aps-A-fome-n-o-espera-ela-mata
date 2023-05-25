package tela1;

import model1.Usuario;
import model1.Conta;
import model1.UsuarioPessoa;
import model1.UsuarioEmpresa;
import model1.Endereco;
import model1.Instituicao;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import utils.Utils;

public class Tela{

    private Scanner scan = new Scanner(System.in);
    private final List<Usuario> listaContribuidores = new ArrayList<>();

    private final List<Instituicao> listaInstituicoes = new ArrayList<>();

	public Tela(){

		listaContribuidores.add(new UsuarioPessoa("Caio", "caio@gmail.com", "senha123", "92106151071", new Endereco("rua1", "12", "complemento", "sp", "sp"), new Conta("Caio", "1234", 6000)));
		listaContribuidores.add(new UsuarioPessoa("Vinicius", "vinicius@gmail.com", "senha123", "05680164086", new Endereco("rua2", "12", "", "sp", "sp"), new Conta("Vinicius", "0001", 4000)));
		listaContribuidores.add(new UsuarioPessoa("Lucas", "lucas@gmail.com", "senha123", "80465679064", new Endereco("rua2", "12", "", "sp", "sp"), new Conta("Lucas", "3254", 200)));
		listaContribuidores.add(new UsuarioPessoa("Victor", "victor@gmail.com", "senha123", "67645636084", new Endereco("rua1", "4", "complemento2", "sp", "sp"), new Conta("Victor", "3214", 1000)));

		listaContribuidores.add(new UsuarioEmpresa("Bradesco", "bradesco@email.com", "senha123", "64619031000108", new Endereco("rua1", "12", "complemento", "São Paulo", "SP"), new Conta("Empresa1", "4522", 20000)));
		listaContribuidores.add(new UsuarioEmpresa("Itau", "itau@email.com", "senha123", "72554697000153", new Endereco("rua2", "24", "complemento", "São Paulo", "SP"), new Conta("Empresa2", "6452", 10000)));
		listaContribuidores.add(new UsuarioEmpresa("Unip", "unip@email.com", "senha123", "14508561000100", new Endereco("rua1", "12", "complemento", "São Paulo", "SP"), new Conta("Empresa3", "4512", 5000)));

		listaInstituicoes.add(new Instituicao("Instituto Heleninha", "heleninha@email.com", "senha123", "54450107000160", "Somos uma organização sem fins lucrativos que desde 1999 oferece o transporte e apoio sociofamiliar a crianças e adolescentes em situação de vulnerabilidade social que se encontram em tratamento de câncer.", new Conta("Heleninha", "6897", 0)));
		listaInstituicoes.add(new Instituicao("Instituto Resgatando Vidas", "regandovidas@email.com", "senha123", "71786929000136", "Localizado na zona norte, o Resgatando Vidas promove ações de cidadania e transformação social nos arredores da Vila Nova Cachoeirinha. Assim, oferece oficinas culturais e esportivas para crianças e adolescentes e cursos de capacitação e qualificação profissional para jovens e adultos.", new Conta("Regando Vidas", "4564", 0)));
		listaInstituicoes.add(new Instituicao("GRAACC", "graacc@email.com", "senha123", "45549108000176", "Na ativa desde 1991, o GRAACC ajuda crianças e adolescentes com câncer. Com hospital próprio, a ONG depende de doações de pessoas e empresas solidárias e parceiras para continuar o trabalho. Voluntários também são aceitos.", new Conta("graacc", "8972", 0)));

		for (Instituicao instituicao : listaInstituicoes){

			Random gerador = new Random();
			int numeroAleatorio = gerador.nextInt(4);

            instituicao.doacaoDinheiro(listaContribuidores.get(numeroAleatorio),gerador.nextDouble(2000));

		}
	}



	public void telaInicial() {

		int escolhaUsuario;

		do{

			escolhaUsuario = 0;

			Utils.decoraTexto("Tela Inicial");

			System.out.println("[0] - Encerrar Programa");
			System.out.println("\n[1] - Login");
			System.out.println("[2] - Resgistrar");

			System.out.print("\nEscolha uma opção: ");

			try{
				escolhaUsuario = Integer.parseInt(this.scan.nextLine());

			} catch (Exception e){
				System.out.println(e);
				continue;
			}

			if (escolhaUsuario != 0 && escolhaUsuario != 1 && escolhaUsuario != 2) {

				System.out.println("Escolha Invalida!");
				continue;
			}

			if (escolhaUsuario == 1) {

				Usuario usuario = this.telaLogin();
				if (usuario == null){
					continue;
				} else {

					this.telaApp(usuario);

				}

			}

			if (escolhaUsuario == 2) {

				this.telaRegistro();

			}


		} while (escolhaUsuario != 0);


		this.telaFinal();
		this.scan.close();

	}

	public void telaRegistro() {

		int escolhaUsuario = 0;

		Usuario usuario = null;

		do {

			Utils.decoraTexto("Tela de Registro");

			System.out.println("[0] - Cancelar\n\n[1] - Pessoa Física\n[2] - Pessoa Jurídica\n[3] - Instituição para doação\n");

			System.out.print("Escolha do Usuario: ");

			try {
				escolhaUsuario = Integer.parseInt(this.scan.nextLine());
			} catch (Exception e) {
				continue;
			}

			if (escolhaUsuario == 0) {

				return;

			} else if (escolhaUsuario == 1) {

				usuario = new UsuarioPessoa();

			} else if (escolhaUsuario == 2){

				usuario = new UsuarioEmpresa();

			} else if (escolhaUsuario == 3){

				usuario = new Instituicao();

			}
		} while(usuario == null);

        if (usuario.getClass().equals(Instituicao.class)){

            Instituicao usuarioTemporario = new Instituicao();
            usuarioTemporario.setNome(usuario.getNome());
            usuarioTemporario.setEmail(usuario.getEmail());
            usuarioTemporario.setSenha(usuario.getSenha());
            usuarioTemporario.setCnpj(usuario.getIdentificador());
            usuarioTemporario.setNome(usuario.getNome());

            usuarioTemporario.registrar(this.listaInstituicoes, this.scan, "");

        } else {
    		usuario.registrar(this.listaContribuidores, this.scan);
        }
        System.out.println("\nUsuario registrado com sucesso! Faça Login");


	}

	public Usuario telaLogin() {

		String email, senha;

		Usuario usuario = null;


		do {

			Utils.decoraTexto("Tela de Login - [Digite 0 para voltar]");

			System.out.print("Email: ");
			email = this.scan.nextLine();

			if (email.equals("0")){

				return null;

			}

			System.out.print("Password: ");
			senha = this.scan.nextLine();

			usuario = Usuario.validaLogin(email, senha, this.listaContribuidores, this.listaInstituicoes);

		} while (usuario == null);

		return usuario;

	}

	public void telaApp(Usuario usuario){

		int escolhaUsuario = 0;

		do {

			this.scan = new Scanner(System.in);

			Utils.decoraTexto("Menu Principal");
			System.out.println("[0] - Sair\n\n[1] - Ver contribuidores cadastrados\n[2] - Ver Instituições\n[3] - Ver minhas informações\n");
			System.out.print("Escolha do Usuario: ");

			try{
				escolhaUsuario = Integer.parseInt(this.scan.nextLine());
			} catch (Exception e){

			}

			if (escolhaUsuario == 1) {

				this.telaContribuidores();

			} else if (escolhaUsuario == 2){

				this.telaInstituicoes(usuario);

			} else if (escolhaUsuario == 3){

				usuario.verInformacoes(this.scan);

			}
		} while (escolhaUsuario != 0);

	}

	public void telaContribuidores(){

		Utils.decoraTexto("Contribuidores");

		for (Usuario contribuidor : listaContribuidores){

			System.out.println(contribuidor.getNome());

		}

		System.out.print("\nPressione enter para voltar...");

		String descartavel = this.scan.nextLine();

	}

	public void telaInstituicoes(Usuario usuario){

		int escolhaUsuario = 0;

		do {

			Utils.decoraTexto("Instituições");
			int contador = 0;

			System.out.println("[0] - Voltar\n");

			for (Usuario instituicao : listaInstituicoes) {

				contador++;
				System.out.println("[" + contador + "] - " + instituicao.getNome());
			}

			System.out.print("\nEscolha uma instituição para ver mais: ");

			try{
				escolhaUsuario = Integer.parseInt(this.scan.nextLine());
			} catch (Exception e){

			}
			if (escolhaUsuario != 0 && escolhaUsuario <= listaInstituicoes.toArray().length && escolhaUsuario > 0){

				Instituicao instituicao = listaInstituicoes.get(escolhaUsuario-1);

                instituicao.detalhes(usuario, scan);

			}

		} while(escolhaUsuario != 0);

	}


	public void telaFinal(){

		scan.close();
		System.out.println("\nEncerrando Programa... Volte Sempre!");

	}
}