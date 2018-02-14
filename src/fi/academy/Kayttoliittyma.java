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
            int komento = Integer.parseInt(lukija.nextLine());

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

//    private void haeReitti(){
//        System.out.println();
//        System.out.print("Anna lähtöpaikka: ");
//        String mista = this.lukija.nextLine();
//
//        System.out.print("Anna pääteasema: ");
//        String minne = this.lukija.nextLine();
//    }



}
