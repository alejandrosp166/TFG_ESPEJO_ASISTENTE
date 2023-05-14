package es.back.tfg.asp.modelo.converters;

import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterUsuario {
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private ConverterCrendenciales converterCrendenciales;

    public Usuario dtoInAEntidad(DTOUsuarioIn dtoUsuarioIn) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dtoUsuarioIn.getNombre());
        usuario.setApellidos(dtoUsuarioIn.getApellidos());
        usuario.setEmail(dtoUsuarioIn.getEmail());
        usuario.setUsername(dtoUsuarioIn.getUsername());
        usuario.setAdmin(dtoUsuarioIn.isEsAdmin());
        // ESTO HAY QUE HACERLO DESPUÉS DE QUE SE GUARDE EL USUARIO EN LA BASE DE DATOS
        usuario.setCredencialesUsuario(converterCrendenciales.dtoInAEntidad(dtoUsuarioIn.getDtoCredencialesIn(), usuario));
        return usuario;
    }

    public DTOUsuarioOut entidadADTOOut(Usuario usuarioEntidad) {
        DTOUsuarioOut dtoUsuarioOut = new DTOUsuarioOut();
        // dtoUsuarioOut.setUuid(usuarioEntidad.getUuid());
        dtoUsuarioOut.setNombre(usuarioEntidad.getUsername());
        dtoUsuarioOut.setApellidos(usuarioEntidad.getApellidos());
        dtoUsuarioOut.setEmail(usuarioEntidad.getEmail());
        dtoUsuarioOut.setEsAdmin(usuarioEntidad.isAdmin());
        // dtoUsuarioOut.setUuidCredenciales(usuarioEntidad.getCredenciales().getUuid());
        return dtoUsuarioOut;
    }

    public List<DTOUsuarioOut> listaEntidadesAListaDTO(List<Usuario> usuarios) {
        List<DTOUsuarioOut> usuariosDtoList = new ArrayList<>();
        for (Usuario u : usuarios) {
            usuariosDtoList.add(entidadADTOOut(u));
        }
        return usuariosDtoList;
    }
}
