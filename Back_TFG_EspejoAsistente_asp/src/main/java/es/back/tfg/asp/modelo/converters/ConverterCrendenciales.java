package es.back.tfg.asp.modelo.converters;

import es.back.tfg.asp.modelo.dto.in.DTOCredencialesIn;
import es.back.tfg.asp.modelo.dto.out.DTOCredencialesOut;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterCrendenciales {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    public CredencialesUsuario dtoInAEntidad(DTOCredencialesIn dtoCredencialesIn, Usuario u) {
        CredencialesUsuario credencialesUsuario = new CredencialesUsuario();
        credencialesUsuario.setPassword(dtoCredencialesIn.getPassword());
        // ESTO DE AQUÍ HAY QUE HACERLO DESPUÉS DE QUE EL USUARIO SE HAYA GUARDADO EN LA BASE DE DATOS
        credencialesUsuario.setUuIdUsuario(u);
        return credencialesUsuario;
    }

    public DTOCredencialesOut entidadADTOOut(CredencialesUsuario credencialesUsuario) {
        return null;
    }
}
