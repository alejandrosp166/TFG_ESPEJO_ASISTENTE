package es.back.tfg.asp.servicio.iservice;

import es.back.tfg.asp.modelo.dto.in.DTOCambioPasswordIn;
import es.back.tfg.asp.modelo.dto.in.DTOEnvioCorreoIn;
import es.back.tfg.asp.modelo.dto.in.DTOIniciarSesion;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;

public interface IServiceAuth {
    public DTOUsuarioOut registrarUsuario(DTOUsuarioIn dtoUsuarioIn);
    public void enviarEmailCambioPassword(DTOEnvioCorreoIn dtoEnvioCorreoIn);
    public void cambiarPassword(DTOCambioPasswordIn dtoCambioPasswordIn);
}
