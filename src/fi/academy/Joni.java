package fi.academy;

// junien haku lähtöaseman perusteella.

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

public class Joni {


    public static void main(String[] args) {
        //haeAsemanPerusteella();
        Joni juna = new Joni();

        String[] veturi = {"               .---._\t\t\t\t\t\t\t\t\t\t",
                "           .--(. '  .).--.      .  .-.  \t\t\t\t\t\t\t",
                "         . ( ' _) .)` (   .)-. ( ) '-'\t\t\t\t\t\t\t",
                "       ( ,  ).        `(' . _)\t\t\t\t\t\t\t\t",
                "      (')  _________      '-'\t\t\t\t\t\t\t\t\t",
                "     ____ [_________]                                        ________\t\t",
                "     \\__/ | _ \\  ||    ,;,;,,                               [________]	",
                "     _][__|(\")/__||  ,;;;;;;;;,   __________   __________   _| JUNA |_\t",
                "    /             | |____      | |          | |  ___     | |      ____|\t",
                "   (| .--.    .--.| |     ___  | |   |  |   | |      ____| |____      |\t",
                "   /|/ .. \\~~/ .. \\_|_.-.__.-._|_|_.-.__.-._|_|_.-.__.-._|_|_.-.__.-._|\t",
                "  /_|\\ '' /~~\\ '' /=+( o )( o )+==( o )( o )=+=( o )( o )+==( o )( o )=+=\t",
                "='==+'-'=='-'Aino==+Aleksi=='-'Elina+='-'Joni===+='-'Milla=='-'==+=='-'=+'-'",
        };


        juna.tulosta(veturi);
    }


        public void tulosta(String[]veturi){
            for (int i = 0; i < veturi.length; i++) {
                System.out.println(veturi[i]);
            }

        }

    }



//    public void haeAsemanPerusteella() {             // haetaan seuraavaksi lähtevää junaa
//        Scanner lukija = new Scanner(System.in);            // käyttäjän valitsemalta asemalta
//        System.out.println();
//        System.out.print("Anna lähtöpaikka: ");
//        //String mistaPitka = ;
//        String mista = haeLyhenne(lukija.nextLine());
//
//        String juna="Juna";
//        String lahto="Lähtee";
//        String mihin= "Määräasema";
//
//        System.out.println("\nLadataan junia...");
//        System.out.println();
//        //System.out.println("Juna "+ " \tLähtee " + " \tMääräasema");
//        System.out.printf("%-10s %-10s %-10s \n", juna, lahto, mihin  );
//        lueJunanJSONData(mista);
//
//    }

//    public void lueJunatLahtoasemanPerusteella(String mista) {
//        String baseurl = "https://rata.digitraffic.fi/api/v1";
//        try {
//            URL url = new URL(baseurl+"/live-trains/station/" + mista + "?departing_trains=5&include_nonstopping=false");
//            ObjectMapper mapper = new ObjectMapper();
//            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
//            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
//
//
//            List<Juna> uusiLista =
//                    junat.stream()
//                    .sorted(Comparator.comparing(juna -> juna.getTimeTableRows().get(0).getScheduledTime()))
//                            .collect(Collectors.toCollection(ArrayList::new));
//
//
//            for (int i = 0; i < uusiLista.size(); i++) {
//                int vikaAika = uusiLista.get(i).getTimeTableRows().size()-1;
//
//                String lahtoaika = uusiLista.get(i).getTimeTableRows().get(0).getScheduledTime().substring(11,16);
//                String tyyppi = uusiLista.get(i).getTrainType() + uusiLista.get(i).getTrainNumber();
//                String maaranpaa = uusiLista.get(i).getTimeTableRows().get(vikaAika).getStationShortCode();
//
//                System.out.printf("%-10s %-10s %-10s  \n", tyyppi, lahtoaika, haePitkaAsema(maaranpaa));       // haePitkaAsema(maaranpaa));
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }

//    private static String haeLyhenne(String asema) {
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
//
//
//    }

//    public String haePitkaAsema(String asema) {
//        String tiedosto = "src/fi/academy/asemienlyhenteet.txt";
//        try (FileReader fr = new FileReader(tiedosto);
//             BufferedReader in = new BufferedReader(fr)) {
//
//            String rivi;
//            while ((rivi = in.readLine()) != null) {
//                String[] palat = rivi.split(";");          // ArrayIndexOutOfBoundsException käsittely!!! Ei välttämättä edes tässä kohdassa
//                if (palat[1].equalsIgnoreCase(asema)) {
//                    return palat[0];
//                }
//            }
//        } catch (IOException ex) {
//            return "Virhe!";
//        }
//        return "Asemaa ei löytynyt";
//
//
//    }

