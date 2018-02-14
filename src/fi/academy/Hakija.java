package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Hakija {

    public void lueMistaMinne(String mista, String minne, String aika) {
        Calendar kalenteri = new GregorianCalendar();
        kalenteri.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aika.substring(0, 2)));
        kalenteri.set(Calendar.MINUTE, Integer.parseInt(aika.substring(3, 5)));
        
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/" + mista + "/" + minne);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            List<Juna> ajallaRajatut = junat.stream()
                    .filter(juna -> juna.getTimeTableRows().get(0).getScheduledTime().after(kalenteri.getTime()))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < ajallaRajatut.size(); i++) {
                String tyyppi;
                if (junat.get(i).getTrainCategory().equals("Commuter")) {
                    tyyppi = junat.get(i).getCommuterLineID();
                } else {
                    tyyppi = junat.get(i).getTrainType() + junat.get(i).getTrainNumber();
                }
                String lahtoaika = ajallaRajatut.get(i).getTimeTableRows().get(0).getScheduledTime().toString().substring(11,16);
                String saapumisaika = ajallaRajatut.get(i).getTimeTableRows().get(haeIndeksi(junat, minne)).getScheduledTime().toString().substring(11,16);
                System.out.printf("%-10s %-10s %-10s \n", tyyppi, lahtoaika, saapumisaika);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void lueJunatLahtoasemanPerusteella(String mista) {
        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/" + mista + "?departing_trains=200&include_nonstopping=false");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            List<Juna> uusiLista =
                    junat.stream()
                            .sorted(Comparator.comparing(juna -> juna.getTimeTableRows().get(0).getScheduledTime()))
                            .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < uusiLista.size(); i++) {
                int vikaAika = uusiLista.get(i).getTimeTableRows().size()-1;

                String tyyppi;
                if (uusiLista.get(i).getTrainCategory().equals("Commuter")) {
                    tyyppi = uusiLista.get(i).getCommuterLineID();
                } else {
                    tyyppi = uusiLista.get(i).getTrainType() + uusiLista.get(i).getTrainNumber();
                }
                String lahtoaika = uusiLista.get(i).getTimeTableRows().get(0).getScheduledTime().toString().substring(11,16);
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

        String mista = virheSyotteidenKasittely(lukija, "Anna lähtöasema: ");

        String juna="Juna";
        String lahto="Lähtee";
        String mihin= "Määräasema";

        System.out.println("\nLadataan junia...");
        System.out.println();
        System.out.printf("%-10s %-10s %-10s \n", juna, lahto, mihin  );
        lueJunatLahtoasemanPerusteella(mista);
    }

    public void haeReitti(){
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
        System.out.printf("%-10s %-10s %-10s \n", "Juna", mistaPitka, minnePitka);

        lueMistaMinne(mista, minne, lahtoAika);
    }

    private String virheSyotteidenKasittely(Scanner lukija, String viesti) {
        String mista;
        while (true) {
            if (lukija.hasNextLine()) {
                mista = haeAsema(lukija.nextLine());

                if (mista.equals("VIRHE")) {
                    System.out.println("Asemaa ei löydy!");
                    System.out.println();
                    System.out.print(viesti);
                    continue;
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