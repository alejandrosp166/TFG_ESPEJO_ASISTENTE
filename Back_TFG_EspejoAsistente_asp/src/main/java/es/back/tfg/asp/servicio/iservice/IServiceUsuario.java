package es.back.tfg.asp.servicio.iservice;


import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;

import java.util.List;

public interface IServiceUsuario {
    void eliminarUsuario(String uuid);
    DTOUsuarioOut actualizarUsuario(DTOUsuarioIn dtoUsuario, String uuid);
    DTOUsuarioOut guardarUsuario(DTOUsuarioIn dtoUsuario);
    List<DTOUsuarioOut> obtenerUsuarios();
    DTOUsuarioOut obtenerUsuarioPorUsername(String username);
    DTOUsuarioOut obtenerUsuarioPorId(String uuid);
    DTOUsuarioOut obtenerUsuarioPorCodigoVerificacion(String codigo);
}
