package es.back.tfg.asp.servicio.iservice;


import es.back.tfg.asp.modelo.dto.DTOUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;

import java.util.List;

public interface ServiceUsuario {
    void eliminarUsuario(String uuid);
    DTOUsuarioOut actualizarUsuario(DTOUsuarioIn dtoUsuarioIn, String uuid);
    DTOUsuarioOut guardarUsuario(DTOUsuarioIn dtoUsuarioIn);
    List<DTOUsuarioOut> obtenerUsuarios();
    DTOUsuarioOut obtenerUsuarioPorId(String uuid);
}
