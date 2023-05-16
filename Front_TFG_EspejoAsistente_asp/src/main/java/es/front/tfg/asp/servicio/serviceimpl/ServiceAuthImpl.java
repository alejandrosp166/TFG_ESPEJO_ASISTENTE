package es.front.tfg.asp.servicio.serviceimpl;

import com.google.gson.Gson;
import es.front.tfg.asp.dtos.DTOCambioPassword;
import es.front.tfg.asp.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;

import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ServiceAuthImpl implements IServiceAuth {
    private final String URL = "http://localhost:8080/v0/api/auth";
    @Autowired
    private PeticionesHTTP peticionesHTTP;
    @Override
    public void registrarUsuario(DTOUsuario dtoUsuario) {
        peticionesHTTP.post(dtoUsuario, URL + "/registro");
    }

    @Override
    public void enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo) {
        peticionesHTTP.post(dtoEnvioCorreo, URL + "/enviar-mail-recuperacion");
    }

    @Override
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword) {
        peticionesHTTP.post(dtoCambioPassword, URL + "/cambiar-password");
    }
}
