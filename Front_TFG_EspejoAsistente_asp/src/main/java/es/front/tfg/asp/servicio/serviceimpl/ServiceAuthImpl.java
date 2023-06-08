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

    /**
     * Peticiona al back para iniciar sesión
     *
     * @param dtoIniciarSesion un dto que contiene el usuario y la contraseña
     * @return un apiResponse
     */
    @Override
    public Object iniciarSesion(DTOIniciarSesion dtoIniciarSesion) {
        return peticionesHTTP.post(dtoIniciarSesion, URL + "/inicio-sesion", DTOUsuarioOut.class);
    }

    /**
     * Peticiona al back para registrar al usuario
     *
     * @param dtoUsuarioIn el usuario a registrar
     * @return un apiResponse
     */
    @Override
    public Object registrarUsuario(DTOUsuarioIn dtoUsuarioIn) {
       return peticionesHTTP.post(dtoUsuarioIn, URL + "/registro", DTOUsuarioOut.class);
    }

    /**
     * Envía un mail de recuperación de cuenta al usuario
     *
     * @param dtoEnvioCorreo un dto con el correo electrónico del usuario
     * @return un ApiResponse
     */
    @Override
    public ApiResponse enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo) {
        return peticionesHTTP.post(dtoEnvioCorreo, URL + "/enviar-mail-recuperacion", ApiResponse.class);
    }

    /**
     * Peticiona al back para cambiar la contraseña del usuario
     *
     * @param dtoCambioPassword un dto con los datos necesarios para cambiar la contraseña
     * @return un apiresponse
     */
    @Override
    public ApiResponse cambiarContrasenna(DTOCambioPassword dtoCambioPassword) {
        return peticionesHTTP.post(dtoCambioPassword, URL + "/cambiar-password", ApiResponse.class);
    }
}
