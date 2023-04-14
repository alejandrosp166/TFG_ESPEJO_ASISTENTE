package es.tfg.asp.servicio.serviceimpl;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.tfg.asp.modelo.entidades.Usuario;
import es.tfg.asp.repositorio.RepositorioCredenciales;
import es.tfg.asp.repositorio.RepositorioUsuario;
import es.tfg.asp.servicio.iservice.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ServiceUsuarioImpl implements ServiceUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;

    @Override
    public boolean validarUsuario(String username, String pass) {
        boolean acceso = false;
        Usuario usuario = repositorioUsuario.buscarPorUsername(username);
        if (Objects.nonNull(usuario)) {
            CredencialesUsuario credenciales = repositorioCredenciales.buscarCredencialesPorIdUsuario(usuario.getIdUsuario());
            // Poner más condiciones Aquí (credenciales.pass == pass)
            if (Objects.nonNull(credenciales)) {
                acceso = true;
            }
        }
        return acceso;
    }
}
