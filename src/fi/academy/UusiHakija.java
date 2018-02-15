package fi.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UusiHakija {
    private JSONDatanLukija datanLukija = new JSONDatanLukija();



    public void tulostaJunatMistaMinne(String mista, String minne, String aika){
        List<Juna> junat = datanLukija.lueDataMistaMinne(mista, minne, aika);

        for (int i = 0; i < junat.size(); i++) {
            Juna juna = junat.get(i);
            String tyyppi = juna.getTrainType() + juna.getTrainNumber();
            String lahtoaika = juna.getTimeTableRows().get(datanLukija.lahtemisenIndeksi(juna, mista)).getScheduledTime().toString().substring(11, 16);
            String saapumisaika = juna.getTimeTableRows().get(datanLukija.saapumisenIndeksi(juna, minne)).getScheduledTime().toString().substring(11, 16);

            Date lahto = junat.get(i).getTimeTableRows().get(datanLukija.lahtemisenIndeksi(juna, mista)).getScheduledTime();
            Date saapum = junat.get(i).getTimeTableRows().get(datanLukija.saapumisenIndeksi(juna, minne)).getScheduledTime();

            long aikaMinuutteina = (saapum.getTime() - lahto.getTime()) / 1000 / 60;
            String matkaaika = aikaMinuutteina/60 + " h " + aikaMinuutteina%60 + " min";
            System.out.printf("%-10s %-10s %-10s %-10s \n", tyyppi, lahtoaika, saapumisaika, matkaaika);
        }
    }



    public void kysyMistaMinne(){
        Scanner lukija = new Scanner(System.in);
        System.out.println();
        System.out.print("Anna lähtöpaikka: ");
        String mista = virheSyotteidenKasittely(lukija, "Anna lähtöasema: ");
        String mistaPitka = haeAsema(mista);

        System.out.print("Anna pääteasema: ");
        String minne = virheSyotteidenKasittely(lukija, "Anna pääteasema: ");
        String minnePitka = haeAsema(minne);

        System.out.println("Haetaanko tämän päivän junat?");
        System.out.println("\t0 = ei");
        System.out.println("\t1 = kyllä");
        int valinta = Integer.parseInt(lukija.nextLine());

        if (valinta == 0){
            System.out.print("Anna lähtöjen päivämäärä muodossa pp.kk.vvvv --> ");
            String paiva = lukija.nextLine();

            System.out.print("Anna lähtoaika muodossa hh:mm --> ");
            String aika = lukija.nextLine();

            System.out.println("\nLadataan junia...\n");
            System.out.printf("%-10s %-10s %-10s %-10s \n", "Juna", mistaPitka, minnePitka, "Matka-aika");
            tulostaPaivanMukaan(mista, minne, aika, paiva);

        } else {
            System.out.print("Anna lähtoaika (muodossa hh:mm): ");
            String lahtoAika = lukija.nextLine();

            System.out.println("\nLadataan junia...");
            System.out.println();
            System.out.printf("%-10s %-10s %-10s %-10s \n", "Juna", mistaPitka, minnePitka, "Matka-aika");

            tulostaJunatMistaMinne(mista, minne, lahtoAika);
        }
    }

    public void tulostaJunatLahtoasemalta(String mista){
        List<Juna> junat = datanLukija.lueDataLahtoasemalta(mista);

        int lukumaara = 10;
        if (junat.size() < lukumaara) {
            lukumaara = junat.size();
        }

        for (int i = 0; i < lukumaara; i++) {
            Juna juna = junat.get(i);
            int vikaAika = junat.get(i).getTimeTableRows().size()-1;

            String tyyppi;

            if (junat.get(i).getTrainCategory().equals("Commuter")) {
                tyyppi = junat.get(i).getCommuterLineID();
            } else {
                tyyppi = junat.get(i).getTrainType() + junat.get(i).getTrainNumber();
            }

            String lahtoaika = juna.getTimeTableRows().get(datanLukija.lahtemisenIndeksi(juna, mista)).getScheduledTime().toString().substring(11, 16);
            String maaranpaa = junat.get(i).getTimeTableRows().get(vikaAika).getStationShortCode();
            System.out.printf("%-10s %-10s %-10s \n", tyyppi, lahtoaika, haeAsema(maaranpaa));
        }
    }

    public void kysyLahtoasemaa() {             // haetaan seuraavaksi lähtevää junaa
        Scanner lukija = new Scanner(System.in);            // käyttäjän valitsemalta asemalta
        System.out.println();
        System.out.print("Anna lähtöpaikka: ");

        String mista = virheSyotteidenKasittely(lukija, "Anna lähtöpaikka: ");

        String juna="Juna";
        String lahto="Lähtee";
        String mihin= "Määräasema";

        System.out.println("\nLadataan junia...");
        System.out.println();
        System.out.printf("%-10s %-10s %-10s \n", juna, lahto, mihin  );
        tulostaJunatLahtoasemalta(mista);
    }

    public void tulostaPaivanMukaan(String mista, String minne, String aika, String paiva){
        List<Juna> junat = this.datanLukija.lueDataPaivanMukaan(mista, minne, aika, paiva);

        for (int i = 0; i < junat.size(); i++) {
            Juna juna = junat.get(i);
            String tyyppi = juna.getTrainType() + juna.getTrainNumber();
            String lahtoaika = juna.getTimeTableRows().get(datanLukija.lahtemisenIndeksi(juna, mista)).getScheduledTime().toString().substring(11, 16);
            String saapumisaika = juna.getTimeTableRows().get(datanLukija.saapumisenIndeksi(juna, minne)).getScheduledTime().toString().substring(11, 16);

            Date lahto = junat.get(i).getTimeTableRows().get(datanLukija.lahtemisenIndeksi(juna, mista)).getScheduledTime();
            Date saapum = junat.get(i).getTimeTableRows().get(datanLukija.saapumisenIndeksi(juna, minne)).getScheduledTime();

            long aikaMinuutteina = (saapum.getTime() - lahto.getTime()) / 1000 / 60;
            String matkaaika = aikaMinuutteina/60 + " h " + aikaMinuutteina%60 + " min";
            System.out.printf("%-10s %-10s %-10s %-10s \n", tyyppi, lahtoaika, saapumisaika, matkaaika);
        }
    }
    
    public String virheSyotteidenKasittely(Scanner lukija, String viesti) {
        String mista;
        while (true) {
            if (lukija.hasNextLine()) {
                mista = haeAsema(lukija.nextLine());

                if (mista.equals("VIRHE")) {
                    System.out.println("Asemaa ei löydy!");
                    System.out.println();
                    System.out.print(viesti);
                    break;
                } else {
                    break;
                }
            } else {
                System.out.println("Tyhjä syöte!");
                System.out.println();
                System.out.print(viesti);
                continue;
            }
        }
        return mista;
    }

    public String haeAsema(String asema) {
        String tiedosto = "src/fi/academy/asemienlyhenteet.txt";
        try (FileReader fr = new FileReader(tiedosto);
             BufferedReader in = new BufferedReader(fr)) {

            String rivi;
            while ((rivi = in.readLine()) != null) {
                String[] palat = rivi.split(";");
                if (palat[0].equalsIgnoreCase(asema)) {
                    return palat[1];
                }
                if (palat[1].equalsIgnoreCase(asema)) {
                    return palat[0];
                }
            }
        } catch (IOException ex) {
            return "Virhe!";
        }
        return "VIRHE";
    }

    public String muokkaaStringSyotetta(String syote) {
        return syote.replaceAll("\\s","" ).replaceAll("\\d","" );

    }

    public void tulostaSaapuvatJunatAsemalle(String minne){
        List<Juna> junat = datanLukija.lueDataSaapumiasemalta(minne);
        // System.out.println(datanLukija.lueDataSaapumiasemalta(minne));


        int lukumaara = 10;
        if (junat.size() < lukumaara) {
            lukumaara = junat.size();
        }

        for (int i = 0; i < lukumaara; i++) {
            Juna juna = junat.get(i);
            int vikaAika = junat.get(i).getTimeTableRows().size()-1;

            String tyyppi;

            if (junat.get(i).getTrainCategory().equals("Commuter")) {
                tyyppi = junat.get(i).getCommuterLineID();
            } else {
                tyyppi = junat.get(i).getTrainType() + junat.get(i).getTrainNumber();
            }

            String lahtopaikka = junat.get(i).getTimeTableRows().get(0).getStationShortCode();
            String saapumisaika = juna.getTimeTableRows().get(datanLukija.saapumisenIndeksi(juna, minne)).getScheduledTime().toString().substring(11, 16);
            String maaranpaa = junat.get(i).getTimeTableRows().get(vikaAika).getStationShortCode();
            System.out.printf("%-10s %-15s %-15s %-15s \n", tyyppi, haeAsema(lahtopaikka), saapumisaika, haeAsema(maaranpaa));
        }
    }

    public void kysySaapumisasemalta() {             // haetaan seuraavaksi lähtevää junaa
        Scanner lukija = new Scanner(System.in);            // käyttäjän valitsemalta asemalta
        System.out.println();
        System.out.print("Anna saapumisasema: ");

        String minne = virheSyotteidenKasittely(lukija, "Anna saapumisasema: ");

        String juna="Juna";
        String lahto="Lähtöpaikka";
        String saapuu="Saapumisaika";
        String mihin= "Määränpää";

        System.out.println("\nLadataan junia...");
        System.out.println();
        System.out.printf("%-10s %-15s %-15s %-15s \n", juna, lahto, saapuu, mihin  );
        tulostaSaapuvatJunatAsemalle(minne);
    }




}
