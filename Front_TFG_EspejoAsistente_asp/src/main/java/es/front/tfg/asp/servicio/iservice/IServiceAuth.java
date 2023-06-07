package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.*;
import es.front.tfg.asp.modelo.response.ApiResponse;

public interface IServiceAuth {
    public DTOUsuarioOut iniciarSesion(DTOIniciarSesion dtoIniciarSesion);
    public void registrarUsuario(DTOUsuarioIn dtoUsuarioIn);
    public ApiResponse enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo);
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword);
}
