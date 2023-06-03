package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.dtos.DTOCambioPassword;
import es.front.tfg.asp.modelo.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.modelo.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;

import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceAuthImpl implements IServiceAuth {
    private final String URL = "http://proxmox.iesmartinezm.es:8102/v0/api/auth";
    @Autowired
    private PeticionesHTTP peticionesHTTP;
    @Override
    public void registrarUsuario(DTOUsuario dtoUsuario) {
        peticionesHTTP.post(dtoUsuario, URL + "/registro", DTOUsuario.class);
    }

    @Override
    public void enviarMailRecuperacion(DTOEnvioCorreo dtoEnvioCorreo) {
        peticionesHTTP.post(dtoEnvioCorreo, URL + "/enviar-mail-recuperacion", null);
    }

    @Override
    public void cambiarContrasenna(DTOCambioPassword dtoCambioPassword) {
        peticionesHTTP.post(dtoCambioPassword, URL + "/cambiar-password", null);
    }
}
