package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUsuarioImpl implements ServiceUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    RepositorioCredenciales repositorioCredenciales;
    @Autowired
    private ConverterUsuario converterUsuario;

    @Override
    public List<DTOUsuarioOut> obtenerUsuarios() {
        return  converterUsuario.listaEntidadesAListaDTO(repositorioUsuario.findAll());
    }

    @Override
    public DTOUsuarioOut obtenerUsuarioPorId(String uuid) {
        Usuario usuario = repositorioUsuario.findUsuarioByUuid(uuid);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut guardarUsuario(DTOUsuarioIn dtoUsuario) {
        Usuario usuario = converterUsuario.dtoInAEntidad(dtoUsuario);
        repositorioUsuario.save(usuario);
        repositorioCredenciales.save(usuario.getCredenciales());
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut actualizarUsuario(DTOUsuarioIn dtoUsuario, String uuid) {
        return null;
    }

    @Override
    public void eliminarUsuario(String uuid) {
        repositorioUsuario.deleteUsuarioByUuid(uuid);
    }
}
