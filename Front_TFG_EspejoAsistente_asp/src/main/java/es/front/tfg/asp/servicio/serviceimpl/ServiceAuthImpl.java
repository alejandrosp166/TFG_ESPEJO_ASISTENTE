package es.front.tfg.asp.servicio.serviceimpl;

import com.google.gson.Gson;
import es.front.tfg.asp.dtos.DTOCambioPassword;
import es.front.tfg.asp.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ServiceAuthImpl implements IServiceAuth {
    private final String URL = "http://localhost:8080/v0/api/auth";

    @Override
    public void registrarUsuario(DTOUsuario dtoUsuario) {
        // REGISTRA EL USUARIO PERO DA UNA EXCEPCIÓN EN FRONT (REVISAR)
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost peticion = new HttpPost(URL + "/registro");
            Gson gson = new Gson();
            String cuerpoPeticion = gson.toJson(dtoUsuario);
            StringEntity dto = new StringEntity(cuerpoPeticion);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            HttpResponse respuesta = client.execute(peticion);
            String cuerpoRespuesta = gson.toJson(respuesta);
            System.out.println(cuerpoRespuesta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost peticion = new HttpPost(URL + "/enviar-mail-recuperacion");
            Gson gson = new Gson();
            String cuerpoPeticion = gson.toJson(dtoEnvioCorreo);
            StringEntity dto = new StringEntity(cuerpoPeticion);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            HttpResponse respuesta = client.execute(peticion);
            String cuerpoRespuesta = gson.toJson(respuesta);
            System.out.println(cuerpoRespuesta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost peticion = new HttpPost(URL + "/cambiar-password");
            Gson gson = new Gson();
            String cuerpoPeticion = gson.toJson(dtoCambioPassword);
            StringEntity dto = new StringEntity(cuerpoPeticion);
            peticion.setEntity(dto);
            peticion.setHeader("Content-type", "application/json");
            HttpResponse respuesta = client.execute(peticion);
            String cuerpoRespuesta = gson.toJson(respuesta);
            System.out.println(cuerpoRespuesta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
