package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.excepciones.ExcepcionBuscarEntidad;
import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioInActualizar;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.Equipo;
import es.back.tfg.asp.modelo.entidades.LocalizacionClima;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioEquipo;
import es.back.tfg.asp.repositorio.RepositorioLocalizacionClima;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.IServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceUsuarioImpl implements IServiceUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioEquipo repositorioEquipo;
    @Autowired
    private RepositorioLocalizacionClima repositorioLocalizacionClima;
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
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(uuid)).orElseThrow(() -> new ExcepcionBuscarEntidad("No se encontró al usuario"));
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut obtenerUsuarioPorUsername(String username) {
        Usuario usuario = repositorioUsuario.findUsuarioByUsername(username);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut obtenerUsuarioPorCodigoVerificacion(String codigo) {
        Usuario usuario = repositorioUsuario.findUsuarioByCodigoVerificacion(codigo);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut guardarUsuario(DTOUsuarioIn dtoUsuario) {
        Usuario usuario = converterUsuario.dtoInAEntidad(dtoUsuario);
        repositorioUsuario.save(usuario);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut actualizarUsuario(DTOUsuarioInActualizar dtoUsuario, String uuid) {
        Usuario usuario = repositorioUsuario.findById(UUID.fromString(uuid)).orElseThrow(() -> new ExcepcionBuscarEntidad("No se encontró al usuario"));
        usuario.setUsername(dtoUsuario.getUsername());
        usuario.setNombre(dtoUsuario.getNombre());
        usuario.setApellidos(dtoUsuario.getApellidos());
        usuario.setAdmin(dtoUsuario.isAdmin());
        usuario.setEmail(dtoUsuario.getEmail());
        Equipo equipo = repositorioEquipo.findById(usuario.getEquipo().getUuid()).orElseThrow(() -> new ExcepcionBuscarEntidad("No se encontró el equipo"));
        equipo.setLiga(dtoUsuario.getEquipo().getLiga());
        equipo.setNombre(dtoUsuario.getEquipo().getNombreEquipo());
        LocalizacionClima localizacionClima = repositorioLocalizacionClima.findById(usuario.getLocalizacionClima().getUuid()).orElseThrow(() -> new ExcepcionBuscarEntidad("No se encontró la localización"));
        localizacionClima.setPais(dtoUsuario.getLocalizacionClima().getPais());
        localizacionClima.setCodigoPostal(dtoUsuario.getLocalizacionClima().getCodigoPostal());
        repositorioEquipo.save(equipo);
        repositorioLocalizacionClima.save(localizacionClima);
        repositorioUsuario.save(usuario);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public void eliminarUsuario(String uuid) {
        repositorioUsuario.deleteById(UUID.fromString(uuid));
    }
}
