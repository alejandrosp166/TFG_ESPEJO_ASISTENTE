package es.front.tfg.asp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.front.tfg.asp.modelo.response.ApiResponse;
import es.front.tfg.asp.modelo.response.ResponseEquipo;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeticionesHTTP {
    private final Gson GSON_MAPPER = new Gson();
    private CloseableHttpClient client = obtenerClienteHttp();
    private final String API_KEY_FOOTBAL = "e61e7d6e91e99d91bb18a7eded870a9e";

    public <T> T post(Object objeto, String url, Class<T> objetoVuelta) {
        T entidad = null;
        try {
            HttpPost peticion = new HttpPost(url);
            String cuerpoPeticion = GSON_MAPPER.toJson(objeto);
            StringEntity dto = new StringEntity(cuerpoPeticion, StandardCharsets.UTF_8);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            CloseableHttpResponse respuesta = client.execute(peticion);
            if (respuesta.getCode() == 201) {
                entidad = GSON_MAPPER.fromJson(EntityUtils.toString(respuesta.getEntity()), objetoVuelta);
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
                entidad = GSON_MAPPER.fromJson(EntityUtils.toString(respuesta.getEntity()), (Type) ApiResponse.class);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return entidad;
    }

    public <T> List<T> getListas(String url, Class<T> claseObjetoDevolver, String container) {
        List<T> listaEntidades = new ArrayList<>();
        try {
            HttpGet peticion = new HttpGet(url);
            peticion.addHeader("x-rapidapi-key", API_KEY_FOOTBAL);
            peticion.addHeader("x-rapidapi-host", "v3.football.api-sports.io");
            CloseableHttpResponse respuesta = client.execute(peticion);

            if (respuesta.getCode() == 200) {
                String json = EntityUtils.toString(respuesta.getEntity());
                JsonObject jsonObject = GSON_MAPPER.fromJson(json, JsonObject.class);
                JsonElement elementoJson = jsonObject.get(container);
                if (elementoJson != null && elementoJson.isJsonArray()) {
                    JsonArray respuestaEnArray = elementoJson.getAsJsonArray();
                    if (respuestaEnArray.size() > 0) {
                        for (int i = 0; i < respuestaEnArray.size(); i++) {
                            listaEntidades.add(GSON_MAPPER.fromJson(respuestaEnArray.get(i), claseObjetoDevolver));
                        }
                    }
                }
            } else {
                listaEntidades = GSON_MAPPER.fromJson(EntityUtils.toString(respuesta.getEntity()), (Type) ApiResponse.class);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return listaEntidades;
    }

    public void put(Object objeto, String url) {
        try {
            HttpPut peticion = new HttpPut(url);
            String cuerpoPeticion = GSON_MAPPER.toJson(objeto);
            StringEntity dto = new StringEntity(cuerpoPeticion, StandardCharsets.UTF_8);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            CloseableHttpResponse respuesta = client.execute(peticion);
            if (respuesta.getCode() != 200) {
                throw new RuntimeException("Error al hacer la petición PUT");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
