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

        System.out.print("Anna lähtoaika (muodossa hh:mm): ");
        String lahtoAika = lukija.nextLine();

        System.out.println("\nLadataan junia...");
        System.out.println();
        System.out.printf("%-10s %-10s %-10s %-10s \n", "Juna", mistaPitka, minnePitka, "Matka-aika");

        tulostaJunatMistaMinne(mista, minne, lahtoAika);
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






}