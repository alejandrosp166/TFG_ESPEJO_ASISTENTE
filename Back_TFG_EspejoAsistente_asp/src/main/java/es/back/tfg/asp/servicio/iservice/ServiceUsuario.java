package es.back.tfg.asp.servicio.iservice;


import es.back.tfg.asp.modelo.dto.DTOUsuario;

public interface ServiceUsuario {
    DTOUsuario registrarUsuario(DTOUsuario usuario);
    boolean validarUsuario(String username, String pass);
    boolean validarUsuarioFaceId();
}
