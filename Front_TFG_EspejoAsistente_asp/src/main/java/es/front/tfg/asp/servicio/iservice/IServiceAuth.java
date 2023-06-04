package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.DTOCambioPassword;
import es.front.tfg.asp.modelo.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;

public interface IServiceAuth {
    public void registrarUsuario(DTOUsuarioIn dtoUsuarioIn);
    public void enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo);
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword);
}
