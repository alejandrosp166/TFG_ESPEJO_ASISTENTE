package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
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
    private ConverterUsuario converterUsuario;

    @Override
    public List<DTOUsuarioOut> obtenerUsuarios() {
        return null;
    }

    @Override
    public DTOUsuarioOut obtenerUsuarioPorId(String uuid) {
        return null;
    }

    @Override
    public DTOUsuarioOut guardarUsuario(DTOUsuarioIn dtoUsuarioIn) {
        return null;
    }

    @Override
    public DTOUsuarioOut actualizarUsuario(DTOUsuarioIn dtoUsuarioIn, String uuid) {
        return null;
    }

    @Override
    public void eliminarUsuario(String uuid) {

    }
}
