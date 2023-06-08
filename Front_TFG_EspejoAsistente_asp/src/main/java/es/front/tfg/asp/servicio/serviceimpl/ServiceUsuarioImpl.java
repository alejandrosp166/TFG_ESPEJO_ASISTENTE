package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
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
    public Object obtenerUsuarioPorCodigoVerificacion(String codigo) {
        return peticionesHTTP.get(URL + "/obtener-por-codigo-verificacion/" + codigo, DTOUsuarioOut.class);
    }

    @Override
    public Object obtenerUsuarioPorUsername(String username) {
        return peticionesHTTP.get(URL + "/obtener-por-username/" + username, DTOUsuarioOut.class);
    }

    @Override
    public Object obtenerUsuarioPorUuid(String uuid) {
        return peticionesHTTP.get(URL + "/" + uuid, DTOUsuarioOut.class);
    }

    @Override
    public Object actualizarUsuario(DTOUsuarioIn usuario, String uuid) {
        return peticionesHTTP.put(usuario, URL + "/" + uuid, DTOUsuarioOut.class);
    }
}
