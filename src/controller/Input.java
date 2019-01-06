package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe que abstrai a utilização da classe Scanner, escondendo todos os
 * problemas relacionados com excepções, e que oferece métodos simples e
 * robustos para a leitura de valores de tipos simples e String.
 *
 * É uma classe de serviços, como Math e outras de Java, pelo que devem ser
 * usados os métodos de classe implementados.
 *
 * Utilizável em BlueJ, NetBeans, CodeBlocks ou Eclipse.
 *
 * Utilização típica:  int x = Input.lerInt();
 *                     String nome = Input.lerString();
 *
 * @author F. Mário Martins
 * @version 1.0 (6/2006)
 */
final class Input {

    static int readInt() {
        final Scanner input = new Scanner(System.in);
        boolean ok = false;
        int i = 0;

        while (!ok) {
            try {
                i = input.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.print("Insert new value: ");
                input.next();
            }
        }

        return i;
    }

    static String readString() {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean ok = false;
        String str = "";

        while (!ok) {
            try {
                str = bufferedReader.readLine();
                ok = true;
            } catch (IOException e) {
                System.out.print("Insert new value: ");
            }
        }

        return str;
    }
}
