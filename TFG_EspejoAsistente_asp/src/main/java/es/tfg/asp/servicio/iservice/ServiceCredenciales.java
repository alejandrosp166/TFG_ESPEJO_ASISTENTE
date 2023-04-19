package es.tfg.asp.servicio.iservice;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.tfg.asp.modelo.entidades.Usuario;
import org.springframework.stereotype.Service;

public interface ServiceCredenciales {
    CredencialesUsuario obtenerCredencialesPorIdUsuario(Usuario usuario);
}
