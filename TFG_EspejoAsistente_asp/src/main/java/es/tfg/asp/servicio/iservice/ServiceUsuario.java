package es.tfg.asp.servicio.iservice;

import es.tfg.asp.modelo.entidades.Usuario;

public interface ServiceUsuario {
    boolean validarUsuario(String username, String pass);
    boolean validarUsuarioFaceId();
}
