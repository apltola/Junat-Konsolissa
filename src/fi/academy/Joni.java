package fi.academy;

// junien haku lähtöaseman perusteella.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joni {
    public static void main(String[] args) {
        haeAsemanPerusteella();
    }

    public static void haeAsemanPerusteella() {             // haetaan seuraavaksi lähtevää junaa
        Scanner lukija = new Scanner(System.in);            // käyttäjän valitsemalta asemalta
        System.out.println();
        System.out.print("Anna lähtöpaikka: ");
        //String mistaPitka = ;
        String mista = haeLyhenne(lukija.nextLine());

        String juna="Juna";
        String lahto="Lähtee";
        String mihin= "Määräasema";

        System.out.println("Haetaan asemalta " + mista +  " lähteviä junia...");
        System.out.println();
        //System.out.println("Juna "+ " \tLähtee " + " \tMääräasema");
        System.out.printf("%-10s %-10s %-10s \n", juna, lahto, mihin  );
        lueJunanJSONData(mista);

    }

    private static void lueJunanJSONData(String mista) {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/" + mista + "?departing_trains=5&include_nonstopping=false");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            // System.out.println(junat.get(0).getTrainNumber());
            // Seuraavaa varten on toteutettava fi.academy.TimeTableRow luokka:
            // System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            //System.out.println(junat.get(0));
            for (int i = 0; i < junat.size(); i++) {
                int vikaAika = junat.get(i).getTimeTableRows().size()-1;

                String tyyppi = junat.get(i).getTrainType() + junat.get(i).getTrainNumber();
                String lahtoaika = junat.get(i).getTimeTableRows().get(0).getScheduledTime().substring(11,16);
                String maaranpaa = junat.get(i).getTimeTableRows().get(vikaAika).getStationShortCode();
                System.out.printf("%-10s %-10s %-10s  \n", tyyppi, lahtoaika, maaranpaa);       // haePitkaAsema(maaranpaa));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static String haeLyhenne(String asema) {
        String tiedosto = "src/fi/academy/asemienlyhenteet.txt";
        try (FileReader fr = new FileReader(tiedosto);
             BufferedReader in = new BufferedReader(fr)) {

            String rivi;
            while ((rivi = in.readLine()) != null) {
                String[] palat = rivi.split(";");
                if (palat[0].equalsIgnoreCase(asema)) {
                    return palat[1];
                }
            }
        } catch (IOException ex) {
            return "Virhe!";
        }
        return "Asemaa ei löytynyt";


    }

    private static String haePitkaAsema(String asema) {
        String tiedosto = "src/fi/academy/asemienlyhenteet.txt";
        try (FileReader fr = new FileReader(tiedosto);
             BufferedReader in = new BufferedReader(fr)) {

            String rivi;
            while ((rivi = in.readLine()) != null) {
                String[] palat = rivi.split(";");          // ArrayIndexOutOfBoundsException käsittely!!! Ei välttämättä edes tässä kohdassa
                if (palat[1].equalsIgnoreCase(asema)) {
                    return palat[0];
                }
            }
        } catch (IOException ex) {
            return "Virhe!";
        }
        return "Asemaa ei löytynyt";


    }
}
