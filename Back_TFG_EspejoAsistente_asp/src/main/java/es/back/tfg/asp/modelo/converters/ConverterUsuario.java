package es.back.tfg.asp.modelo.converters;

import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConverterUsuario {

    public Usuario dtoAEntidad() {
        return null;
    }

    public DTOUsuarioOut entidadADTO(Usuario usuarioEntidad) {
        return null;
    }

    public List<DTOUsuarioOut> listaEntidadesAListaDTO(List<Usuario> usuarios) {
        return null;
    }
}
