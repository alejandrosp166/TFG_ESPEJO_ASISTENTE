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
        usuario.setIdUsuario(dtoUsuarioIn.getIdUsuario());
        usuario.setNombre(dtoUsuarioIn.getNombre());
        usuario.setApellidos(dtoUsuarioIn.getApellidos());
        usuario.setEmail(dtoUsuarioIn.getEmail());
        usuario.setUuid(dtoUsuarioIn.getUuid());
        usuario.setUsername(dtoUsuarioIn.getUsername());
        usuario.setEsAdmin(dtoUsuarioIn.isEsAdmin());
        usuario.setCredenciales(converterCrendenciales.dtoInAEntidad(dtoUsuarioIn.getDtoCredencialesIn(), usuario));
        return usuario;
    }

    public DTOUsuarioOut entidadADTOOut(Usuario usuarioEntidad) {
        return null;
    }

    public List<DTOUsuarioOut> listaEntidadesAListaDTO(List<Usuario> usuarios) {
        List<DTOUsuarioOut> usuariosDtoList = new ArrayList<>();
        for (Usuario u : usuarios) {
            usuariosDtoList.add(entidadADTOOut(u));
        }
        return usuariosDtoList;
    }
}
