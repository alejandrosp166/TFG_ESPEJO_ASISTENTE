package es.tfg.asp.servicio.iservice;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import org.springframework.stereotype.Service;

public interface ServiceCredenciales {
    CredencialesUsuario obtenerCredencialesPorIdUsuario(int id);
}
