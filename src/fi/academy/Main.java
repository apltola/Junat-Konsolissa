package fi.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        haeLyhenne();
    }

    private static void haeLyhenne() {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Kerro asema: ");
        String asema = lukija.nextLine();

        try (FileReader fr = new FileReader("src/fi/academy/asemienlyhenteet.txt");
             BufferedReader in = new BufferedReader(fr)) {

            String rivi;
            while ((rivi = in.readLine()) != null) {
                String[] palat = rivi.split(";");
                if (palat[0].equalsIgnoreCase(asema)) {
                    System.out.println(palat[1]);
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Virhe!");
        }
    }
}
