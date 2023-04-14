package es.tfg.asp.servicio.serviceimpl;

import es.tfg.asp.repositorio.RepositorioCredenciales;
import es.tfg.asp.servicio.iservice.ServiceCredenciales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCredencialesImpl implements ServiceCredenciales {
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;
}
