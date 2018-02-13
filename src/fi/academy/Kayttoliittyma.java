package fi.academy;

import java.util.Scanner;

public class Kayttoliittyma {
    private Scanner lukija;

    public Kayttoliittyma(Scanner lukija) {
        this.lukija = lukija;
    }

    public void kayta(){
        System.out.println("TERVETULOA 1980-LUVULLE");
        System.out.println("\nValitse komento syöttämällä numero: ");
        System.out.println("\t0 = Sulje ohjelma");
        System.out.println("\t1 = Hae reitti");
        System.out.println("\t2 = Hae lähtevät junat aseman mukaan");
        System.out.println("\t3 = Hae saapuvat junat aseman mukaan");
        System.out.println("\t4 = Hae junan tiedot");

        boolean quit = false;
        while (!quit){
            System.out.print("\nSyötä komento --> ");
            int komento = Integer.parseInt(lukija.nextLine());

            switch (komento){
                case 0:
                    quit = true;
                    break;

                case 1:
                    haeReitti();

                    break;

                case 2:
                    break;
            }


        }
    }

    private void haeReitti(){
        System.out.println();
        System.out.print("Anna lähtöpaikka: ");
        String mista = this.lukija.nextLine();

        System.out.print("Anna pääteasema: ");
        String minne = this.lukija.nextLine();
    }

    private void haeLahtevat(){

    }

    private void haeSaapuvat(){

    }

}
