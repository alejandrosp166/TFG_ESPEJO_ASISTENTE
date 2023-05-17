package es.front.tfg.asp.utils;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PeticionesHTTP {
    private final Gson GSON_MAPPER = new Gson();
    private CloseableHttpClient client = obtenerClienteHttp();

    public <T> T post(Object objeto, String url, Class<T> claseObjetoDevolver) {
        T entidad = null;
        try {
            HttpPost peticion = new HttpPost(url);
            String cuerpoPeticion = GSON_MAPPER.toJson(objeto);
            StringEntity dto = new StringEntity(cuerpoPeticion);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            CloseableHttpResponse respuesta = client.execute(peticion);
            if (respuesta.getCode() == 201) {
                entidad = GSON_MAPPER.fromJson(EntityUtils.toString(respuesta.getEntity()), claseObjetoDevolver);
            } else {
                throw new RuntimeException("Error al hacer la petición POST");
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return entidad;
    }

    public <T> T get(String url, Class<T> claseObjetoDevolver) {
        T entidad = null;
        try {
            HttpGet peticion = new HttpGet(url);
            CloseableHttpResponse respuesta = client.execute(peticion);

            if (respuesta.getCode() == 200) {
                entidad = GSON_MAPPER.fromJson(EntityUtils.toString(respuesta.getEntity()), claseObjetoDevolver);
            } else {
                throw new RuntimeException("Error al hacer la petición GET");
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return entidad;
    }

    public void delete(String url) {
        try {
            HttpDelete peticion = new HttpDelete(url);
            CloseableHttpResponse response = client.execute(peticion);
            if (response.getCode() != 204) {
                throw new RuntimeException("Error al hacer la petición DELETE");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CloseableHttpClient obtenerClienteHttp() {
        if (client == null) {
            client = HttpClients.createDefault();
        }
        return client;
    }
}
