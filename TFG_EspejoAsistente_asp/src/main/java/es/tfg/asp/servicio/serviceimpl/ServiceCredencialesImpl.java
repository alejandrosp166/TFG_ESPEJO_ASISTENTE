package es.tfg.asp.servicio.serviceimpl;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.tfg.asp.modelo.entidades.Usuario;
import es.tfg.asp.repositorio.RepositorioCredenciales;
import es.tfg.asp.servicio.iservice.ServiceCredenciales;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCredencialesImpl implements ServiceCredenciales {
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;

    @Override
    public CredencialesUsuario obtenerCredencialesPorIdUsuario(Usuario usuario) {
        CredencialesUsuario credenciales;
        try {
            credenciales = repositorioCredenciales.buscarCredencialesPorIdUsuario(usuario);
        } catch (Exception e) {
            throw new EntityNotFoundException("error de loco manin");
        }
        return credenciales;
    }
}
