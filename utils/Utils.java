package utils;


public class Utils {

    public static void decoraTexto(String texto) {

        int caracteres = texto.length() + 1;

        for (int i = 0; i <= caracteres; i++) {

            System.out.print("=");

        }

        System.out.println("\n " + texto);

        for (int i = 0; i <= caracteres; i++) {

            System.out.print("=");

        }

        System.out.println(" ");

    }

}