package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Hakija {

    public void lueMistaMinne(String mista, String minne) {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/" + mista + "/" + minne);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            for (int i = 0; i < junat.size(); i++) {
                String tyyppi = junat.get(i).getTrainType() + junat.get(i).getTrainNumber();
                String lahtoaika = junat.get(i).getTimeTableRows().get(0).getScheduledTime().toString().substring(11,16);
                String saapumisaika = junat.get(i).getTimeTableRows().get(haeIndeksi(junat, minne)).getScheduledTime().toString().substring(11,16);
                System.out.printf("%-10s %-10s %-10s \n", tyyppi, lahtoaika, saapumisaika);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void lueJunatLahtoasemanPerusteella(String mista) {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/" + mista + "?departing_trains=5&include_nonstopping=false");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            List<Juna> uusiLista =
                    junat.stream()
                            .sorted(Comparator.comparing(juna -> juna.getTimeTableRows().get(0).getScheduledTime()))
                            .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < uusiLista.size(); i++) {
                int vikaAika = uusiLista.get(i).getTimeTableRows().size()-1;

                String lahtoaika = uusiLista.get(i).getTimeTableRows().get(0).getScheduledTime().toString().substring(11,16);
                String tyyppi = uusiLista.get(i).getTrainType() + uusiLista.get(i).getTrainNumber();
                String maaranpaa = uusiLista.get(i).getTimeTableRows().get(vikaAika).getStationShortCode();

                System.out.printf("%-10s %-10s %-10s  \n", tyyppi, lahtoaika, haeAsema(maaranpaa));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void haeJunatLahtoasemanPerusteella() {             // haetaan seuraavaksi lähtevää junaa
        Scanner lukija = new Scanner(System.in);            // käyttäjän valitsemalta asemalta
        System.out.println();
        System.out.print("Anna lähtöpaikka: ");
        String mista;
        while (true) {
            if (lukija.hasNextLine()) {
                mista = haeAsema(lukija.nextLine());

                if (mista.equals("VIRHE")) {
                    System.out.println("Asemaa ei löydy!");
                    System.out.print("Anna lähtöpaikka: ");
                    continue;
                } else {
                    break;
                }
            } else {
                System.out.println("Tyhjä syöte!");
                System.out.print("Anna lähtöpaikka: ");
                continue;
            }
        }

        String juna="Juna";
        String lahto="Lähtee";
        String mihin= "Määräasema";

        System.out.println("\nLadataan junia...");
        System.out.println();
        //System.out.println("Juna "+ " \tLähtee " + " \tMääräasema");
        System.out.printf("%-10s %-10s %-10s \n", juna, lahto, mihin  );
        lueJunatLahtoasemanPerusteella(mista);

    }

    public void haeReitti(){
        Scanner lukija = new Scanner(System.in);
        System.out.println();
        System.out.print("Anna lähtöpaikka: ");
        String mista;

        while (true) {
            if (lukija.hasNextLine()) {
                mista = haeAsema(lukija.nextLine());

                if (mista.equals("VIRHE")) {
                    System.out.println("Asemaa ei löydy!");
                    System.out.print("Anna lähtöpaikka: ");
                    continue;
                } else {
                    break;
                }
            } else {
                System.out.println("Tyhjä syöte!");
                System.out.print("Anna lähtöpaikka: ");
                continue;
            }
        }

        String mistaPitka = haeAsema(mista);

        System.out.print("Anna pääteasema: ");
        String minne;

        while (true) {
            if (lukija.hasNextLine()) {
                minne = haeAsema(lukija.nextLine());

                if (minne.equals("VIRHE")) {
                    System.out.println("Asemaa ei löydy!");
                    System.out.print("Anna pääteasema: ");
                    continue;
                } else {
                    break;
                }
            } else {
                System.out.println("Tyhjä syöte!");
                System.out.print("Anna pääteasema: ");
                continue;
            }
        }
        String minnePitka = haeAsema(minne);

        System.out.println("Ladataan junia...");
        System.out.println();
        System.out.printf("%-10s %-10s %-10s \n", "Juna", mistaPitka, minnePitka);
        lueMistaMinne(mista, minne);
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
        return "joku pikkuasema";
    }

    public int haeIndeksi(List<Juna> lista, String lyhenne){
        for (int i = 0; i < lista.size(); i++) {
            List<TimeTableRow> ajat = lista.get(i).getTimeTableRows();

            for (int j = 0; j < ajat.size(); j++) {
                if (ajat.get(j).getStationShortCode().equals(lyhenne) && ajat.get(j).getType().equals("ARRIVAL")){
                    return j;
                }
            }
        }
        return -1;
    }
}