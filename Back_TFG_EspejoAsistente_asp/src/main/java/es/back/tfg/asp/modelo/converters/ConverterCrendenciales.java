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
        credencialesUsuario.setIdCredenciales(dtoCredencialesIn.getIdCredenciales());
        credencialesUsuario.setPassword(dtoCredencialesIn.getPassword());
        credencialesUsuario.setIdUsuario(u);
        return credencialesUsuario;
    }

    public DTOCredencialesOut entidadADTOOut(CredencialesUsuario credencialesUsuario) {
        return null;
    }
}
