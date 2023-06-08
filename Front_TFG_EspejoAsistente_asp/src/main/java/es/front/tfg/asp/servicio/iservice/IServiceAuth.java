package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.*;
import es.front.tfg.asp.modelo.response.ApiResponse;

public interface IServiceAuth {
    public Object iniciarSesion(DTOIniciarSesion dtoIniciarSesion);
    public Object registrarUsuario(DTOUsuarioIn dtoUsuarioIn);
    public ApiResponse enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo);
    public ApiResponse cambiarContrasenna(DTOCambioPassword dtoCambioPassword);
}
