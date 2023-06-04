package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;

public interface IServiceUsuario {

    public DTOUsuarioOut obtenerUsuarioPorUuid(String uuid);
    public void actualizarUsuario(DTOUsuarioIn usuario, String uuid);
    public DTOUsuarioOut obtenerUsuarioPorCodigoVerificacion(String codigo);
    public DTOUsuarioOut obtenerUsuarioPorUsername(String username);
}
