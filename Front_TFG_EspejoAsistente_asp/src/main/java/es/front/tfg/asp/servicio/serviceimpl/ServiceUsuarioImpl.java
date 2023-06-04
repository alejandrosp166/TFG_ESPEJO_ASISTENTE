package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUsuarioImpl implements IServiceUsuario {
    private final String URL = "http://proxmox.iesmartinezm.es:8102/v0/api/usuario";
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    @Override
    public DTOUsuarioIn obtenerUsuarioPorCodigoVerificacion(String codigo) {
        return peticionesHTTP.get(URL + "/obtener-por-codigo-verificacion/" + codigo, DTOUsuarioIn.class);
    }

    @Override
    public DTOUsuarioIn obtenerUsuarioPorUsername(String username) {
        return peticionesHTTP.get(URL + "/obtener-por-username/" + username, DTOUsuarioIn.class);
    }

    @Override
    public DTOUsuarioIn obtenerUsuarioPorUuid(String uuid) {
        return peticionesHTTP.get(URL + "/" + uuid, DTOUsuarioIn.class);
    }

    @Override
    public void actualizarUsuario(DTOUsuarioIn usuario, String uuid) {
        peticionesHTTP.put(usuario, URL + "/" + uuid, DTOUsuarioIn.class);
    }
}
