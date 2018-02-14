package fi.academy;

import java.util.Scanner;

public class Kayttoliittyma {
    private Scanner lukija;
    private Hakija hakijaOlio;

    public Kayttoliittyma(Scanner lukija) {
        this.lukija = lukija;
        this.hakijaOlio = new Hakija();
    }

    public void kayta(){
        System.out.println("TERVETULOA 1980-LUVULLE");
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
                    System.out.println("\nKiitos käytöstä!");
                    quit = true;
                    break;

                case 1:
                    this.hakijaOlio.haeReitti();
                    break;

                case 2:
                    this.hakijaOlio.haeJunatLahtoasemanPerusteella();
                    break;

                case 3:
                    break;

                case 4:
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
}
