package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.dtos.*;
import es.front.tfg.asp.modelo.response.ApiResponse;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;

import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceAuthImpl implements IServiceAuth {
    private final String URL = "http://proxmox.iesmartinezm.es:8102/v0/api/auth";
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    @Override
    public DTOUsuarioOut iniciarSesion(DTOIniciarSesion dtoIniciarSesion) {
        return peticionesHTTP.post(dtoIniciarSesion, URL + "/inicio-sesion", DTOUsuarioOut.class);
    }

    @Override
    public void registrarUsuario(DTOUsuarioIn dtoUsuarioIn) {
        peticionesHTTP.post(dtoUsuarioIn, URL + "/registro", DTOUsuarioOut.class);
    }

    @Override
    public ApiResponse enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo) {
        return peticionesHTTP.post(dtoEnvioCorreo, URL + "/enviar-mail-recuperacion", ApiResponse.class);
    }

    @Override
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword) {
        peticionesHTTP.post(dtoCambioPassword, URL + "/cambiar-password", null);
    }
}
