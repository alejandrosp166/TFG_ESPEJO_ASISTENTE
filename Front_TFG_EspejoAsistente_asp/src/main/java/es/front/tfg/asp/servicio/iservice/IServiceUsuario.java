package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.dtos.DTOUsuario;

public interface IServiceUsuario {
    public void actualizarUsuario(DTOUsuario usuario);
    public DTOUsuario obtenerUsuarioPorCodigoVerificacion(String codigo);
    public DTOUsuario obtenerUsuarioPorUsername(String username);
}
