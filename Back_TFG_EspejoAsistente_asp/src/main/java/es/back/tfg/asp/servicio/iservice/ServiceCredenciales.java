package es.back.tfg.asp.servicio.iservice;

import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;

public interface ServiceCredenciales {
    CredencialesUsuario obtenerCredencialesPorIdUsuario(Usuario usuario);
}
