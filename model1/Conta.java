package model1;

import java.util.Objects;
import java.util.Scanner;

public class Conta {

	private String titular;
	private String numero;
	private double saldo;

	public Conta(){}

	public Conta(String titular, String numero, double saldo){

		this.titular = titular;
		this.numero = numero;
		this.saldo = saldo;

	}

	public void registrar(Scanner scan){

		do{
			System.out.print("\nDigite o nome do Titular da conta: ");
			this.titular = scan.nextLine();

		} while(this.titular.isBlank());

		do {

			System.out.print("Digite o número da Conta: ");
			this.numero = scan.nextLine();

		} while(this.numero.isBlank());

	}

	public void depositar(double valor) {

		if (valor <= 0) {

			return;

		}

		this.saldo += valor;

	}

	public void sacar(double valor) {

		if (valor <= 0 || valor > this.saldo) {

			return;

		}

		this.saldo -= valor;

	}

	public boolean transferir(Conta destino, double valor) {

		if (this.equals(destino) || valor > this.saldo) {

			return false;

		}

		this.sacar(valor);
		destino.depositar(valor);
		return true;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero, saldo, titular);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(numero, other.numero)
				&& Double.doubleToLongBits(saldo) == Double.doubleToLongBits(other.saldo)
				&& Objects.equals(titular, other.titular);
	}

}
