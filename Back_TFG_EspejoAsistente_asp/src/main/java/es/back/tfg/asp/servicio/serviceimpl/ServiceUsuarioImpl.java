package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.dto.DTOUsuario;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.ServiceCredenciales;
import es.back.tfg.asp.servicio.iservice.ServiceUsuario;
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
    public DTOUsuario registrarUsuario(DTOUsuario usuario) {
        return null;
    }

    @Override
    public boolean validarUsuario(String username, String pass) {
        /*
        FALTAN HACER VALIDACIONES DE LOS DATOS QUE INTRODUCE EL USUARIO PARA VALIDAR TODAS LAS EXCEPCIONES
         */
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

    @Override
    public boolean validarUsuarioFaceId() {
        return false;
    }
}
