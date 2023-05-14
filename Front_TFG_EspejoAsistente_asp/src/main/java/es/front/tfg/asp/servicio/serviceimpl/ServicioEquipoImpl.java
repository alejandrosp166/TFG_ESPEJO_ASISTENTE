package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.servicio.iservice.IServiceEquipo;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.springframework.stereotype.Service;

@Service
public class ServicioEquipoImpl implements IServiceEquipo {
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
    }
}