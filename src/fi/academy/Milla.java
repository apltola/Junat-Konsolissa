package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//Milla koodaa junien hakua junan numeron perusteella
public class Milla {

    public static void main(String[] args) {

        String junanNumero = "143";
        String alkuUrl = "https://rata.digitraffic.fi/api/v1/";
        try {
            URL urlLiike = new URL(alkuUrl + "trains/latest/" + junanNumero);
            ObjectMapper mapper = new ObjectMapper();
            CollectionType millan = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junaLista = mapper.readValue(urlLiike, millan);

            if (!junaLista.get(0).isRunningCurrently()) {
                System.out.println("Juna ei ole tällä hetkellä liikkeessä");
            } else {
                try {
                    URL urlAsemat = new URL(alkuUrl + "train-tracking/latest/" + junanNumero + "?version=1000");
                    ObjectMapper mapperUusi = new ObjectMapper();
                    CollectionType millanUusi = mapperUusi.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
                    List<Juna> junaUusiLista = mapperUusi.readValue(urlAsemat, millanUusi);
                    String asemat = "Välillä: " + junaLista.get(0).getStation() + " - " + junaLista.get(0).getNextStation();
                    System.out.println(asemat);
                } catch(IOException f){
                    System.out.println(f);
            }
        }

        } catch (IOException e) {
            System.out.println(e);
        }

//Miten kysytään:
    }
}
