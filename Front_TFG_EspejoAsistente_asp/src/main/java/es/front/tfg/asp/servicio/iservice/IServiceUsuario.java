package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;

public interface IServiceUsuario {

    public Object obtenerUsuarioPorUuid(String uuid);
    public Object actualizarUsuario(DTOUsuarioIn usuario, String uuid);
    public Object obtenerUsuarioPorCodigoVerificacion(String codigo);
    public Object obtenerUsuarioPorUsername(String username);
}
