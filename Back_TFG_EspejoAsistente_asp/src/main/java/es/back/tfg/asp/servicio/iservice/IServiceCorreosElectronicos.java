package es.back.tfg.asp.servicio.iservice;

import es.back.tfg.asp.modelo.dto.in.DTOEnvioCorreoIn;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface IServiceCorreosElectronicos {
    public void enviarCorreoElectronico(Context contextPlantilla, String nombrePlantilla, DTOEnvioCorreoIn dtoEnvioCorreoIn) throws MessagingException;
}
