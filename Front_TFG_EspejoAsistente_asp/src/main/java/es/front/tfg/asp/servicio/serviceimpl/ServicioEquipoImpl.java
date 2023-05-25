package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;

import es.front.tfg.asp.utils.PeticionesHTTP;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEquipoImpl implements IServiceEquipo {
    private final String URL = "https://v3.football.api-sports.io/teams?league=LIGA&season=TEMP";
    @Autowired
    private PeticionesHTTP peticionesHTTP;
    /*
    @Override
    public void obtenerEquipos() {
        try {
            String url = "https://v3.football.api-sports.io/leagues";
            String apiKey = "e61e7d6e91e99d91bb18a7eded870a9e";
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.setHeader("x-rapidapi-key", "" + apiKey);
            request.addHeader("content-type", "application/json");
            HttpResponse response = client.execute(request);
            //String responseBody = EntityUtils.toString(response.getEntity());
            //System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public List<DTOEquipo> obtenerEquiposLigaSantander() {
        return peticionesHTTP.getApiEquipos(URL.replace("LIGA", "39").replace("TEMP", "2022"), DTOEquipo.class);
    }
}
