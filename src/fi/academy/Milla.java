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

        String junanNumero = "47";
        String alkuUrl = "https://rata.digitraffic.fi/api/v1/train-tracking/latest/";
        try {
            URL url = new URL(alkuUrl + junanNumero + "?version=1000");
            ObjectMapper mapper = new ObjectMapper();
            CollectionType millan = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junaLista = mapper.readValue(url, millan);
            System.out.println("Välillä:");
            System.out.print(junaLista.get(0).getStation() + "-");
            System.out.println(junaLista.get(0).getNextStation());

        } catch (IOException e){
            System.out.println(e);
        }

//Miten kysytään:

    }
}
