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

    /**
     * Obtiene un usuario por su código de verificación
     *
     * @param codigo el código a obtener
     * @return un apiResponse
     */
    @Override
    public Object obtenerUsuarioPorCodigoVerificacion(String codigo) {
        return peticionesHTTP.get(URL + "/obtener-por-codigo-verificacion/" + codigo, DTOUsuarioOut.class);
    }

    @Override
    public void eliminarUsuario(String uuid) {
        peticionesHTTP.delete(URL + "/" + uuid);
    }

    /**
     * Obtiene el usuario por su username
     *
     * @param username el username del usuario
     * @return un apiReponse
     */
    @Override
    public Object obtenerUsuarioPorUsername(String username) {
        return peticionesHTTP.get(URL + "/obtener-por-username/" + username, DTOUsuarioOut.class);
    }

    /**
     * Obtiene el usuario por su uuid
     *
     * @param uuid el uuid del usuario
     * @return un apiResponse
     */
    @Override
    public Object obtenerUsuarioPorUuid(String uuid) {
        return peticionesHTTP.get(URL + "/" + uuid, DTOUsuarioOut.class);
    }

    /**
     * Actualiza el usuario
     *
     * @param usuario los datos del usuario
     * @param uuid    el id del usuario a actualizar
     * @return un apiResponse
     */
    @Override
    public Object actualizarUsuario(DTOUsuarioIn usuario, String uuid) {
        return peticionesHTTP.put(usuario, URL + "/" + uuid, DTOUsuarioOut.class);
    }
}
