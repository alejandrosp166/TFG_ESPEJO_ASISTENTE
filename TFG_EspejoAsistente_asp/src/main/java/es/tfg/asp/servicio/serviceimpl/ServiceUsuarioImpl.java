package es.tfg.asp.servicio.serviceimpl;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.tfg.asp.modelo.entidades.Usuario;
import es.tfg.asp.repositorio.RepositorioUsuario;
import es.tfg.asp.servicio.iservice.ServiceCredenciales;
import es.tfg.asp.servicio.iservice.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ServiceUsuarioImpl implements ServiceUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private ServiceCredenciales serviceCredenciales;

    @Override
    public boolean validarUsuario(String username, String pass) {
        boolean acceso = false;
        // Obtenemos el usuario por su username
        Usuario usuario = repositorioUsuario.buscarPorUsername(username);
        // Comprobamos que el usuario existe
        if (Objects.nonNull(usuario)) {
            // Obtenemos las credenciales de ese usuario
            CredencialesUsuario credenciales = serviceCredenciales.obtenerCredencialesPorIdUsuario(usuario);
            // Comprobamos si existen credenciales para ese usuario y si coinciden con las credenciales pasadas por parámetro
            if (Objects.nonNull(credenciales) && credenciales.getPassword().equals(pass)) {
                acceso = true;
            }
        }
        return acceso;
    }
}
