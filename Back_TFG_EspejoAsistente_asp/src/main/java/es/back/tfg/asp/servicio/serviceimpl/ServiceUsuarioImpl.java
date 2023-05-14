package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceUsuarioImpl implements ServiceUsuario {
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
        CredencialesUsuario credencialesUsuario = new CredencialesUsuario(dtoUsuario.getPassword(), usuario);
        repositorioUsuario.save(usuario);
        usuario.setCredencialesUsuario(credencialesUsuario);
        repositorioCredenciales.save(credencialesUsuario);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut actualizarUsuario(DTOUsuarioIn dtoUsuario, String uuid) {
        return null;
    }

    @Override
    public void eliminarUsuario(String uuid) {
        repositorioUsuario.deleteById(UUID.fromString(uuid));
    }
}
