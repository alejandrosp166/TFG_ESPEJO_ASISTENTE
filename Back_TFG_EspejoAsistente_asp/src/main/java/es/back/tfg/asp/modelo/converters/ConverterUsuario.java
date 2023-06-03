package es.back.tfg.asp.modelo.converters;

import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterUsuario {

    public Usuario dtoInAEntidad(DTOUsuarioIn dtoUsuarioIn) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dtoUsuarioIn.getUsername());
        usuario.setNombre(dtoUsuarioIn.getNombre());
        usuario.setApellidos(dtoUsuarioIn.getApellidos());
        usuario.setEmail(dtoUsuarioIn.getEmail());
        usuario.setAdmin(dtoUsuarioIn.isAdmin());
        return usuario;
    }

    public DTOUsuarioOut entidadADTOOut(Usuario usuarioEntidad) {
        DTOUsuarioOut dtoUsuarioOut = new DTOUsuarioOut();
        dtoUsuarioOut.setUsername(usuarioEntidad.getUsername());
        dtoUsuarioOut.setUuid(usuarioEntidad.getUuid());
        dtoUsuarioOut.setNombre(usuarioEntidad.getNombre());
        dtoUsuarioOut.setApellidos(usuarioEntidad.getApellidos());
        dtoUsuarioOut.setEmail(usuarioEntidad.getEmail());
        dtoUsuarioOut.setEsAdmin(usuarioEntidad.isAdmin());
        dtoUsuarioOut.setTokenSeguridad(usuarioEntidad.getTokenSeguridad());
        dtoUsuarioOut.setCodigoVerificacionCambioContrasenna(usuarioEntidad.getCodigoVerificacionCambioContrasenna());
        dtoUsuarioOut.setUuidCredenciales(usuarioEntidad.getCredencialesUsuario().getUuid());
        dtoUsuarioOut.setEquipoFav(usuarioEntidad.getEquipo().getNombre());
        dtoUsuarioOut.setUuidEquipo(usuarioEntidad.getEquipo().getUuid());
        dtoUsuarioOut.setLocalizacion(usuarioEntidad.getLocalizacionClima().getNombre());
        dtoUsuarioOut.setUuidLocalizacion(usuarioEntidad.getLocalizacionClima().getUuid());
        return dtoUsuarioOut;
    }

    public List<DTOUsuarioOut> listaEntidadesAListaDTOOut(List<Usuario> usuarios) {
        List<DTOUsuarioOut> usuariosDtoList = new ArrayList<>();
        for (Usuario u : usuarios) {
            usuariosDtoList.add(entidadADTOOut(u));
        }
        return usuariosDtoList;
    }
}
