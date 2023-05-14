package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.IServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IServiceUsuarioImpl implements IServiceUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;
    @Autowired
    private ConverterUsuario converterUsuario;

    @Override
    public List<DTOUsuarioOut> obtenerUsuarios() {
        return converterUsuario.listaEntidadesAListaDTOOut(repositorioUsuario.findAll());
    }

    @Override
    public DTOUsuarioOut obtenerUsuarioPorId(String uuid) {
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("ERROR"));
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut guardarUsuario(DTOUsuarioIn dtoUsuario) {
        Usuario usuario = converterUsuario.dtoInAEntidad(dtoUsuario);
        repositorioUsuario.save(usuario);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut actualizarUsuario(DTOUsuarioIn dtoUsuario, String uuid) {
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("ERROR"));
        usuario.setUsername(dtoUsuario.getUsername());
        usuario.setNombre(dtoUsuario.getNombre());
        usuario.setApellidos(dtoUsuario.getApellidos());
        usuario.setAdmin(dtoUsuario.isAdmin());
        usuario.setEmail(dtoUsuario.getEmail());
        repositorioUsuario.save(usuario);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public void eliminarUsuario(String uuid) {
        repositorioUsuario.deleteById(UUID.fromString(uuid));
    }
}
