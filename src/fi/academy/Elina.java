//package fi.academy;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.type.CollectionType;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Elina {
//    // reittihaun toteuttaminen: junien etsiminen lähtö- ja määräaeman perusteella
//
//    public static void main(String[] args) {
//        //haeReitti();
//    }
//
//    private void lueMistaMinne(String mista, String minne) {
//        String baseurl = "https://rata.digitraffic.fi/api/v1";
//        try {
//            URL url = new URL(baseurl+"/live-trains/station/" + mista + "/" + minne);
//            ObjectMapper mapper = new ObjectMapper();
//            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
//            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
//
//
//            for (int i = 0; i < junat.size(); i++) {
//                int vikaAika = junat.get(i).getTimeTableRows().size()-1;
//
//                String tyyppi = junat.get(i).getTrainType() + junat.get(i).getTrainNumber();
//                String lahtoaika = junat.get(i).getTimeTableRows().get(0).getScheduledTime().substring(11,16);
//                String saapumisaika = junat.get(i).getTimeTableRows().get(vikaAika).getScheduledTime().substring(11,16);
//                System.out.printf("%-10s %-10s %-10s \n", tyyppi, lahtoaika, saapumisaika);
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }
//
//    public void haeReitti(){
//        Scanner lukija = new Scanner(System.in);
//        System.out.println();
//        System.out.print("Anna lähtöpaikka: ");
//        String mista = haeLyhenne(lukija.nextLine());
//
//        System.out.print("Anna pääteasema: ");
//        String minne = haeLyhenne(lukija.nextLine());
//
//        System.out.println("Ladataan junia...");
//        System.out.println();
//        System.out.printf("%-10s %-10s %-10s \n", " ", mista, minne);
//        lueMistaMinne(mista, minne);
//    }
//
//    private String haeLyhenne(String asema) {
//        String tiedosto = "src/fi/academy/asemienlyhenteet.txt";
//        try (FileReader fr = new FileReader(tiedosto);
//             BufferedReader in = new BufferedReader(fr)) {
//
//            String rivi;
//            while ((rivi = in.readLine()) != null) {
//                String[] palat = rivi.split(";");
//                if (palat[0].equalsIgnoreCase(asema)) {
//                    return palat[1];
//                }
//            }
//        } catch (IOException ex) {
//            return "Virhe!";
//        }
//        return "Asemaa ei löytynyt";
//    }
//}
