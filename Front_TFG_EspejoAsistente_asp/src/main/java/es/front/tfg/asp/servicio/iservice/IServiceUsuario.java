package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;

public interface IServiceUsuario {

    public DTOUsuarioIn obtenerUsuarioPorUuid(String uuid);
    public void actualizarUsuario(DTOUsuarioIn usuario, String uuid);
    public DTOUsuarioIn obtenerUsuarioPorCodigoVerificacion(String codigo);
    public DTOUsuarioIn obtenerUsuarioPorUsername(String username);
}
