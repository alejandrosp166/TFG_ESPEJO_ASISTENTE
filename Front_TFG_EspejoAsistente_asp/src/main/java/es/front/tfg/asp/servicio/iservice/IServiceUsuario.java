package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.dtos.DTOUsuario;

public interface IServiceUsuario {
    public DTOUsuario obtenerUsuarioPorCodigoVerificacion(String codigo);
}
