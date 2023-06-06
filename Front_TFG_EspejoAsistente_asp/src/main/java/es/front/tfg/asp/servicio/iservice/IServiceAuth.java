package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.*;

public interface IServiceAuth {
    public DTOUsuarioOut iniciarSesion(DTOIniciarSesion dtoIniciarSesion);
    public void registrarUsuario(DTOUsuarioIn dtoUsuarioIn);
    public void enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo);
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword);
}
