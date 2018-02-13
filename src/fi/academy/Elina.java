package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Elina {
    // reittihaun toteuttaminen: junien etsiminen lähtö- ja määräaeman perusteella

    public static void main(String[] args) {
        haeReitti();
    }

    private static void lueJunanJSONData(String mista, String minne) {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/" + mista + "/" + minne);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            // System.out.println(junat.get(0).getTrainNumber());
            // Seuraavaa varten on toteutettava fi.academy.TimeTableRow luokka:
            // System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            System.out.println("");
            //System.out.println(junat.get(0));
            for (int i = 0; i < junat.size(); i++) {
                int vikaAika = junat.get(i).getTimeTableRows().size()-1;

                System.out.print(junat.get(i).getTrainType() + junat.get(i).getTrainNumber());
                System.out.print(" " + junat.get(i).getTimeTableRows().get(0).getScheduledTime().substring(11,16));
                System.out.println(" - " + junat.get(i).getTimeTableRows().get(vikaAika).getScheduledTime().substring(11,16));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static void haeReitti(){
        Scanner lukija = new Scanner(System.in);
        System.out.print("Anna lähtöpaikka: ");
        String mista = haeLyhenne(lukija.nextLine());

        System.out.print("Anna pääteasema: ");
        String minne = haeLyhenne(lukija.nextLine());

        System.out.println(mista + " - " + minne);
        lueJunanJSONData(mista, minne);
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
}
