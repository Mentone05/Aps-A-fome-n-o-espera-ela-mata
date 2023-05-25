package model1;

import utils.Utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instituicao extends Usuario{

    private List<Usuario> contribuidores = new ArrayList<>();
    private String descricao;
    private String cnpj;
    private double alimentosDoados;

    public Instituicao(){}

    public Instituicao(String nome, String email, String senha, String cnpj, String descricao, Conta conta) {
        super(nome, email, senha, conta);
        this.descricao = descricao;
        this.cnpj = cnpj;
    }

    public void registrar(List<Instituicao> listaInstituicoes ,Scanner scan, String descartavel){

        do {

            System.out.print("\nDigite o nome: ");
            this.setNome(scan.nextLine());

        } while (this.getNome().isBlank());

        do {

            System.out.print("Digite uma senha: ");
            this.setSenha(scan.nextLine());

        } while (!validaSenha(this.getSenha()));

        do {
            System.out.print("Digite o cnpj: ");
            this.cnpj = scan.nextLine();
            this.setIdentificador(this.cnpj);

        } while (!validaCnpj(this.cnpj, listaInstituicoes));

        do {
            System.out.print("Digite uma descrição sobre a Instituição: ");
            this.descricao = scan.nextLine();

        } while (descricao.isBlank());

        do {

            System.out.print("Digite um email para contato: ");
            this.setEmail(scan.nextLine());

        } while (!validaEmail(this.getEmail()));


        this.setConta(new Conta());
        this.getConta().registrar(scan);

        listaInstituicoes.add(this);

    }

    public void verInformacoes(Scanner scan){

        Utils.decoraTexto("Informações do Usuário");

        System.out.println("Nome: " + this.getNome());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Data do Registro: " + this.getDataRegistro());
        System.out.println("\nCONTA:\nTitular: " + this.getConta().getTitular());
        System.out.println("Número: " + this.getConta().getNumero());
        System.out.println("Saldo: " + this.getConta().getSaldo());

        System.out.print("\nPressione enter para continuar...");

        String descartavel = scan.nextLine();

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

    public static Usuario validaLogin(String email, String senha, List<Usuario> listaInstituicoes){

        for (Usuario instituicao : listaInstituicoes){

            if (instituicao.getEmail().equals(email)){

                if (instituicao.getSenha().equals(senha)){

                    return instituicao;

                }

            }

        }

        System.out.println("Login Inválido!");

        return null;


    }

    public static boolean validaCnpj(String cnpj, List<Instituicao> listaUsuarios){

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

                if (((UsuarioEmpresa) usuario).getCnpj().equals(cnpj)){

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

    public void detalhes(Usuario usuario, Scanner scan){

        int escolhaUsuario = 0;

        do {

            Utils.decoraTexto("Detalhes de \"" + this.getNome() + "\"");
            System.out.println("Email para contato: " + this.getEmail());
            System.out.println("Data do Registro: " + this.getDataRegistro());
            System.out.println("Descrição Abaixo\n\n" + this.descricao);

            if (usuario.getClass() == Instituicao.class){

                System.out.println("\n[0] - Voltar\n[1] - Ver Contribuidores\n");

            } else {
                System.out.println("\n[0] - Voltar\n[1] - Ver Contribuidores\n[2] - Doar\n");
            }

            System.out.print("Escolha do Usuário: ");
            try {
                escolhaUsuario =  Integer.parseInt(scan.nextLine());
            } catch (Exception e){
                continue;
            }

            if (escolhaUsuario == 1){

                this.verContribuidores(scan);

            } else if (escolhaUsuario == 2 && usuario.getClass() != Instituicao.class){

                this.doacao(usuario, scan);

            }

        }while (escolhaUsuario != 0);

    }

    public void doacao(Usuario usuario, Scanner scan){

        String escolhaUser = "0";

        do{

            Utils.decoraTexto("Tela de Doação");

            System.out.print("[0] - Voltar\n\n[1] - Doar Alimentos em Kg\n[2] - Doar em R$\n\nEscolha do Usuario: ");

            escolhaUser = scan.nextLine();

            if (escolhaUser.equals("1")){

                doacaoAlimentos(usuario, scan);

            } else if (escolhaUser.equals("2")) {

                doacaoDinheiro(usuario, scan);

            } else {

                if (!escolhaUser.equals("0")){

                    System.out.println("\nEscolha Inválida!\n");
                }

            }

        }while(!escolhaUser.equals("0"));

    }

    public void doacaoDinheiro(Usuario usuario, Scanner scan){

        double valorDoacao;

        do {


            Utils.decoraTexto("Tela de Doação - [Digite 0 para sair]");

            System.out.print("Digite o valor para doar: R$");

            valorDoacao = Double.parseDouble(scan.nextLine().replace(",", "."));

            if (valorDoacao <= usuario.getConta().getSaldo() && valorDoacao > 0){

                boolean resultado = usuario.getConta().transferir(this.getConta(), valorDoacao);

                if (resultado) {

                    System.out.println("\nDoação de R$" + valorDoacao + " realizada com sucesso!");

                    usuario.setTotalDinheiroDoado(usuario.getTotalDinheiroDoado() + valorDoacao);

                    if (!this.contribuidores.contains(usuario)){

                        this.contribuidores.add(usuario);

                    }

                    System.out.println("Você tem uma redução total no Imposto de Renda de: R$" + usuario.checarReducaoImposto() + ",00");

                    return;

                } else {
                    System.out.println("Ocorreu um erro na transação!");
                }
                
            } else {

                System.out.println("\nSaldo Insuficiente!\n");

            }
        } while (valorDoacao != 0);

    }

    public void doacaoDinheiro(Usuario usuario, double valorDoacao){


        if (valorDoacao <= usuario.getConta().getSaldo() && valorDoacao > 0){

            boolean resultado = usuario.getConta().transferir(this.getConta(), valorDoacao);

            if (resultado) {

                if (!this.contribuidores.contains(usuario)){

                    this.contribuidores.add(usuario);

                }
            }

        }
    }

    public void doacaoAlimentos(Usuario usuario, Scanner scan){

        double valorDoacao;

        do {


            Utils.decoraTexto("Tela de Doação - [Digite 0 para sair]");

            System.out.print("Digite a quantidade para doar em Kg: ");

            valorDoacao = Double.parseDouble(scan.nextLine().toLowerCase().replace(",", ".").replace("k", "").replace("g", ""));

            if (valorDoacao > 0){

                System.out.println("\nDoação de " + valorDoacao + "Kg realizada com sucesso!");

                usuario.setTotalAlimentosDoado(usuario.getTotalAlimentosDoado() + valorDoacao);

                if (!this.contribuidores.contains(usuario)){

                    this.contribuidores.add(usuario);

                }

                System.out.println("Você tem uma redução total no Imposto de Renda de: R$" + usuario.checarReducaoImposto() + ",00");

                return;

            } else {

                System.out.println("\nSaldo Insuficiente!\n");

            }
        } while (valorDoacao != 0);

    }

    public void verContribuidores(Scanner scan){

        Utils.decoraTexto("Lista de Contribuidores da Instituição " + this.getNome());

        for(Usuario contribuidor : this.contribuidores){

            System.out.println(contribuidor.getNome());

        }

        System.out.print("\nPressione enter para continuar...");

        String descartavel = scan.nextLine();

    }

    public void adicionarContribuidor(Usuario contribuidor){
        this.contribuidores.add(contribuidor);
    }

    public List<Usuario> getContribuidores() {
        return contribuidores;
    }

    public void setContribuidores(List<Usuario> contribuidores) {
        this.contribuidores = contribuidores;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }  
    
    public String getCnpj() {
        return descricao;
    }

    public void setCnpj(String descricao) {
        this.descricao = descricao;
    }

}



