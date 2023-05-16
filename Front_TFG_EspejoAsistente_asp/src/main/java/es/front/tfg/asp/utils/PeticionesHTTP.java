package es.front.tfg.asp.utils;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class PeticionesHTTP {
    private final Gson gson = new Gson();
    public void post(Object objeto, String url) {
        try {
            HttpClient client = obtenerClienteHttp();
            HttpPost peticion = new HttpPost(url);
            String cuerpoPeticion = gson.toJson(objeto);
            StringEntity dto = new StringEntity(cuerpoPeticion);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            client.execute(peticion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T get(String url, Class<T> claseObjetoDevolver) {
        try {
            CloseableHttpClient client = obtenerClienteHttp();
            HttpGet peticion = new HttpGet(url);
            CloseableHttpResponse respuesta = client.execute(peticion);

            if(respuesta.getCode() != 200) {
                throw new RuntimeException("Error en la solicitud");
            }
            String cadenaRespuesta = EntityUtils.toString(respuesta.getEntity());
            return gson.fromJson(cadenaRespuesta, claseObjetoDevolver);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private CloseableHttpClient obtenerClienteHttp() {
        return HttpClients.createDefault();
    }
}
