package fi.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class JSONDatanLukija {

    public List<Juna> lueDataMistaMinne(String mista, String minne, String aika){
        Calendar kalenteri = new GregorianCalendar();
        kalenteri.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aika.substring(0, 2)));
        kalenteri.set(Calendar.MINUTE, Integer.parseInt(aika.substring(3, 5)));

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(URI.create(baseurl + "/live-trains/station/" + mista + "/" + minne).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junaLista = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            List<Juna> rajattuLista = junaLista.stream()
                    .filter(juna -> juna.getTimeTableRows().get(lahtemisenIndeksi(juna, mista))
                            .getScheduledTime().after(kalenteri.getTime()))
                    .collect(Collectors.toCollection(ArrayList::new));
            return rajattuLista;
        } catch (MismatchedInputException e) {
            System.out.println("Suoraa junayhteyttä ei löydy!");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new ArrayList<>();
    }

    public List<Juna> lueDataLahtoasemalta(String mista){
        Calendar kalenteri = new GregorianCalendar();

        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {

            URL url = new URL(URI.create(baseurl+"/live-trains/station/" + mista + "?departing_trains=200&include_nonstopping=false").toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            List<Juna> ajallaRajatut = junat.stream()
                    .filter(juna -> juna.getTimeTableRows().get(lahtemisenIndeksi(juna, mista)).getScheduledTime().after(kalenteri.getTime()))
                    .collect(Collectors.toCollection(ArrayList::new));

            List<Juna> rajatutJarjestyksessa =
                    ajallaRajatut.stream()
                            .sorted(Comparator.comparing(juna -> juna.getTimeTableRows().get(lahtemisenIndeksi(juna, mista)).getScheduledTime()))
                            .collect(Collectors.toCollection(ArrayList::new));

            return rajatutJarjestyksessa;
        } catch (Exception ex){
            System.out.println(ex);
        }

        return new ArrayList<>();
    }

    public List<Juna> lueDataPaivanMukaan(String mista, String minne, String aika, String paiva){

        String vuosi = paiva.substring(6,10);
        String kk = paiva.substring(3,5);
        String p = paiva.substring(0,2);
        String formatoituPaiva = vuosi + "-" + kk + "-" + p;

        Calendar kalenteri = new GregorianCalendar();
        kalenteri.set(Calendar.YEAR, Integer.parseInt(vuosi));
        kalenteri.set(Calendar.MONTH, Integer.parseInt(paiva.substring(3,5))-1);
        kalenteri.set(Calendar.DAY_OF_MONTH, Integer.parseInt(p));

        kalenteri.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aika.substring(0, 2)));
        kalenteri.set(Calendar.MINUTE, Integer.parseInt(aika.substring(3, 5)));


        String baseurl = "https://rata.digitraffic.fi/api/v1";
        try {
            URL url = new URL(URI.create(baseurl + "/live-trains/station/" + mista + "/" + minne + "?departure_date=" + formatoituPaiva).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);

            List<Juna> junaLista = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi
            List<Juna> rajattuLista = junaLista.stream()
                    .filter(juna -> juna.getTimeTableRows().get(lahtemisenIndeksi(juna, mista)).getScheduledTime().after(kalenteri.getTime()))
                    .collect(Collectors.toCollection(ArrayList::new));
            return rajattuLista;
        } catch (MismatchedInputException e) {
            System.out.println("Suoraa junayhteyttä ei löydy!");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new ArrayList<>();
    }



    public int lahtemisenIndeksi(Juna juna, String lyhenne){
        List<TimeTableRow> aikataulu = juna.getTimeTableRows();
        for (int i = 0; i < aikataulu.size(); i++) {
            if (aikataulu.get(i).getStationShortCode().equals(lyhenne)
                    && aikataulu.get(i).getType().equals("DEPARTURE")){
                return i;
            }
        }
        return 0;
    }

    public int saapumisenIndeksi(Juna juna, String lyhenne){
        List<TimeTableRow> aikataulu = juna.getTimeTableRows();
        for (int i = 0; i < aikataulu.size(); i++) {
            if (aikataulu.get(i).getStationShortCode().equals(lyhenne)
                    && aikataulu.get(i).getType().equals("ARRIVAL")){
                return i;
            }
        }

        return 0;
    }
}
