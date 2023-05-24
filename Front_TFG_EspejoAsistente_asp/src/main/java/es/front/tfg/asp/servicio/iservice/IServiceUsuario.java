package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.DTOUsuario;

public interface IServiceUsuario {

    public DTOUsuario obtenerUsuarioPorUuid(String uuid);
    public void actualizarUsuario(DTOUsuario usuario, String uuid);
    public DTOUsuario obtenerUsuarioPorCodigoVerificacion(String codigo);
    public DTOUsuario obtenerUsuarioPorUsername(String username);
}
