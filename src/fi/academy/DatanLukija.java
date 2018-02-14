package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatanLukija {
    private Scanner lukija;
    private Juna juna;
    private ArrayList<Juna> junat;

    public DatanLukija() {
        this.juna = new Juna();
        this.junat = new ArrayList<>();
        this.lukija = lukija;

    }

    public ArrayList<Juna> getJunat() {
        return junat;
    }

    public List<Juna> lueJunanJSONData(){
//        System.out.print("Anna lähtöpaikka: ");
//        String lahtoPaikka = lukija.nextLine();
//
//        System.out.print("Anna saapumispaikka: ");
//        String saapumispaikka = lukija.nextLine();

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(baseurl+"/live-trains/station/HKI/LH");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junaLista = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            //System.out.println(junaLista.get(1).getTrainNumber());

            // Seuraavaa varten on toteutettava TimeTableRow luokka:
            //System.out.println(junaLista.get(0).getTimeTableRows().get(0).getScheduledTime());

            //System.out.println("\njunat.get(0):");
            //System.out.println(junaLista.get(1));

            return junaLista;

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new ArrayList<>();
    }

    public void testiMetodi(){
        List<Juna> jokulista = lueJunanJSONData();
        Juna ekaJuna = jokulista.get(0);
        System.out.println(ekaJuna);

//        List<TimeTableRow> ttRows = ekaJuna.getTimeTableRows();
//        String ekaRow = ttRows.get(0).getScheduledTime();
//
//        ttRows.stream(, 2018-02-13T09:27:00.000Z).forEach(System.out::println);
//        System.out.println("size: " + ttRows.size());
//
//        System.out.println(ekaRow);
//        System.out.println(ekaRow.substring(11, 16));
    }

    public void printtaaAjat(){
        List<Juna> jokuLista = lueJunanJSONData();
        Juna ekaJuna = jokuLista.get(0);
        System.out.println(ekaJuna.getTimeTableRows().get(0).getStationShortCode());

        List<TimeTableRow> aikataulu = ekaJuna.getTimeTableRows();

        aikataulu.stream()
                .map(rivi-> rivi.getScheduledTime().toString().substring(11, 16))
                .forEach(System.out::println);

    }


    public void printtaaPaikat(){

    }
}
