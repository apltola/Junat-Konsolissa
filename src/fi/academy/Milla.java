package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Milla  extends Hakija {

    public static void main(String[] args) {
        new Milla().kysyJunannumero();
    }

    public void kysyJunannumero() throws NumberFormatException {
        Scanner lukija = new Scanner(System.in);
        String junanNumero;
        int pelkkaNumero;
        while (true) {
            try {
                System.out.print("\nAnna junan numero: ");
                junanNumero = lukija.nextLine();
                pelkkaNumero = Integer.parseInt(junanNumero.replaceAll("\\D", "").replaceAll("\\s", ""));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Tuntematon junannumero");
                continue;
            }
        }
        System.out.println("\nLadataan tietoja...");
        haeNumeronPerusteella(pelkkaNumero);
    }

    public void haeNumeronPerusteella(int numero) {

        String alkuUrl = "https://rata.digitraffic.fi/api/v1/";
        try {
            URL urlLiike = new URL(alkuUrl + "trains/latest/" + numero);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junaLista = mapper.readValue(urlLiike, tyyppi);

            if (!junaLista.get(0).isRunningCurrently()) {
                System.out.println("\nJuna ei ole tällä hetkellä liikkeessä");
            } else {
                try {
                    URL urlAsemat = new URL(alkuUrl + "train-tracking/latest/" + numero + "?version=1000");
                    ObjectMapper mapperUusi = new ObjectMapper();
                    CollectionType tyyppiUusi = mapperUusi.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
                    List<Juna> junaUusiLista = mapperUusi.readValue(urlAsemat, tyyppiUusi);

                    String nykyinenAsema = haeAsema(junaUusiLista.get(0).getStation());

                    if (nykyinenAsema.equals("VIRHE"))
                        nykyinenAsema = junaUusiLista.get(0).getStation();
                    if (junaUusiLista.get(0).getStation() == null)
                        nykyinenAsema = " ";

                    String seuraavaAsema = haeAsema(junaUusiLista.get(0).getNextStation());
                    if (seuraavaAsema.equals("VIRHE"))
                        seuraavaAsema = junaUusiLista.get(0).getNextStation();
                    if (junaUusiLista.get(0).getNextStation() == null)
                        seuraavaAsema = " ";
                    System.out.println("\nVälillä: " + nykyinenAsema + " - " + seuraavaAsema);

                } catch (Exception f){
                    System.out.println(f);
                }
            }
        } catch (Exception e) {
            System.out.println("\nTuntematon junan numero"); }
    }
}
