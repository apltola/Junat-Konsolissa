package fi.academy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Kayttoliittyma {
    private Scanner lukija;
    private Milla milla;
    private UusiHakija hakija;

    public Kayttoliittyma(Scanner lukija) {
        this.lukija = lukija;
        this.milla = new Milla();
        this.hakija = new UusiHakija();
    }

    public void kayta(){

        printtaaLogo();
        printtaaJunanKuva();
        //printtaaEsittely();

        printtaaKomennot();

        boolean quit = false;
        while (!quit){
            System.out.print("\nSyötä komento --> ");

            String syote = lukija.nextLine();
            int komento;

            if (syote.equals("0") || syote.equals("1") || syote.equals("2") || syote.equals("3") || syote.equals("4") || syote.equals("5")) {
                komento = Integer.parseInt(syote);
            } else {
                System.out.println("Tuntematon komento!");
                continue;
            }

            switch (komento){
                case 0:
                    System.out.println("\nTervetuloa käyttämään sovellusta uudelleen!");
                    quit = true;
                    break;

                case 1:
                    this.hakija.kysyMistaMinne();
                    break;

                case 2:
                    this.hakija.kysyLahtoasemaa();
                    break;

                case 3:
                    this.hakija.kysySaapumisasemalta();
                    break;

                case 4:
                    this.milla.haeNumeronPerusteella();
                    break;

                case 5:
                    printtaaKomennot();
                    break;

            }
        }
    }

    private void printtaaKomennot(){
        System.out.println("\nValitse komento syöttämällä numero: ");
        System.out.println("\t0 = Sulje ohjelma");
        System.out.println("\t1 = Hae reitti");
        System.out.println("\t2 = Hae seuraavat asemalta lähtevät junat");
        System.out.println("\t3 = Hae saapuvat junat aseman mukaan");
        System.out.println("\t4 = Hae junan tiedot");
        System.out.println("\t5 = Tulosta komennot");
    }

    private void printtaaLogo(){
        try {
            Files.lines(Paths.get("logo.txt")).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Virhe logon lukemisessa");
        }
    }

    private void printtaaEsittely(){
        try {
            Files.lines(Paths.get("esittelyteksti.txt")).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Virhe esittelyn lukemisessa");
        }

        System.out.println("PAINA ENTER");
        lukija.nextLine();
    }

    private void printtaaJunanKuva(){
        try {
            Files.lines(Paths.get("junanKuva.txt")).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Virhe kuvan lukemisessa");
        }

        System.out.print("\n\n\nPAINA ENTER: ");
        this.lukija.nextLine();


    }

}
