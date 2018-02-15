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

import static java.util.Collections.replaceAll;

//Milla koodaa junien hakua junan numeron perusteella
public class Milla  extends Hakija{

    public static void main(String[] args) {
        new Milla().haeNumeronPerusteella(); }

    public void haeNumeronPerusteella() {

        Scanner lukija = new Scanner(System.in);
        System.out.print("\nAnna junan numero: ");
        String junanNumero = lukija.nextLine();
        int pelkkaNumero = Integer.parseInt(junanNumero.replaceAll("\\D", "").replaceAll("\\s", ""));

        String alkuUrl = "https://rata.digitraffic.fi/api/v1/";
        try {
            URL urlLiike = new URL(alkuUrl + "trains/latest/" + pelkkaNumero);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType millan = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junaLista = mapper.readValue(urlLiike, millan);

            if (!junaLista.get(0).isRunningCurrently()) {
                System.out.println("\nJuna ei ole tällä hetkellä liikkeessä");
            } else {
                try {
                    URL urlAsemat = new URL(alkuUrl + "train-tracking/latest/" + pelkkaNumero + "?version=1000");
                    ObjectMapper mapperUusi = new ObjectMapper();
                    CollectionType millanUusi = mapperUusi.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
                    List<Juna> junaUusiLista = mapperUusi.readValue(urlAsemat, millanUusi);
                    String nykyinenAsema = haeAsema(junaUusiLista.get(0).getStation());
                    if (nykyinenAsema.equals("VIRHE")) { nykyinenAsema = junaUusiLista.get(0).getStation();}
                    if (junaUusiLista.get(0).getStation() == null) {nykyinenAsema = " ";}
                    String seuraavaAsema = haeAsema(junaUusiLista.get(0).getNextStation());
                   if (seuraavaAsema.equals("VIRHE")) { seuraavaAsema = junaUusiLista.get(0).getNextStation();}
                    if (junaUusiLista.get(0).getNextStation() == null) {seuraavaAsema = " ";}
                    String asemat = "\nVälillä: " + nykyinenAsema + " - "
                            + seuraavaAsema;
                    System.out.println(asemat);
                   // System.out.println("Klo: " + new Date().toString().substring(11, 16));
                } catch(IOException f){
                    System.out.println("\nVIRHE: " + f); } }
        } catch (Exception e) {
            System.out.println("\nTuntematon junan numero"); }


}



}
