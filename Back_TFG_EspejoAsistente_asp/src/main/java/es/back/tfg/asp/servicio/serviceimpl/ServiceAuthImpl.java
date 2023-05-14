package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.IServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceAuthImpl implements IServiceAuth {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;
    @Autowired
    private ConverterUsuario converterUsuario;
    @Override
    public DTOUsuarioOut registrarUsuario(DTOUsuarioIn dtoUsuarioIn) {
        Usuario usuario = converterUsuario.dtoInAEntidad(dtoUsuarioIn);
        CredencialesUsuario credencialesUsuario = new CredencialesUsuario(dtoUsuarioIn.getPassword(), usuario);
        repositorioUsuario.save(usuario);
        usuario.setCredencialesUsuario(credencialesUsuario);
        repositorioCredenciales.save(credencialesUsuario);
        return converterUsuario.entidadADTOOut(usuario);
    }
}
