package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUsuarioImpl implements IServiceUsuario {
    private final String URL = "http://localhost:8080/v0/api/usuario";
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    @Override
    public DTOUsuario obtenerUsuarioPorCodigoVerificacion(String codigo) {
        return peticionesHTTP.get(URL + "/obtener-por-codigo-verificacion/" + codigo, DTOUsuario.class);
    }

    @Override
    public DTOUsuario obtenerUsuarioPorUsername(String username) {
        return peticionesHTTP.get(URL + "/obtener-por-username/" + username, DTOUsuario.class);
    }

    @Override
    public void actualizarUsuario(DTOUsuario usuario) {
        peticionesHTTP.put(usuario, URL + "idUsuario", DTOUsuario.class);
    }
}
