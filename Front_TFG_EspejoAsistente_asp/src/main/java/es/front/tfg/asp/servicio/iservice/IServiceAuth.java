package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.dtos.DTOCambioPassword;
import es.front.tfg.asp.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.dtos.DTOUsuario;

public interface IServiceAuth {
    public void registrarUsuario(DTOUsuario dtoUsuario);
    public void enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo);
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword);
}
