package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.excepciones.ExcepcionCambiarContrasenna;
import es.back.tfg.asp.excepciones.ExcepcionCorreoElectronico;
import es.back.tfg.asp.excepciones.ExcepcionInicioSesion;
import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOCambioPasswordIn;
import es.back.tfg.asp.modelo.dto.in.DTOEnvioCorreoIn;
import es.back.tfg.asp.modelo.dto.in.DTOIniciarSesion;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Equipo;
import es.back.tfg.asp.modelo.entidades.LocalizacionClima;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioEquipo;
import es.back.tfg.asp.repositorio.RepositorioLocalizacionClima;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.IServiceAuth;
import es.back.tfg.asp.servicio.iservice.IServiceCorreosElectronicos;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;

@Service
public class ServiceAuthImpl implements IServiceAuth {
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;
    @Autowired
    private RepositorioEquipo repositorioEquipo;
    @Autowired
    private RepositorioLocalizacionClima repositorioLocalizacionClima;
    @Autowired
    private IServiceCorreosElectronicos serviceCorreosElectronicos;
    @Autowired
    private ConverterUsuario converterUsuario;


    @Override
    public DTOUsuarioOut iniciarSesion(DTOIniciarSesion dtoIniciarSesion) {
        Usuario usuario = repositorioUsuario.findUsuarioByUsername(dtoIniciarSesion.getUsername());
        CredencialesUsuario credencialesUsuario = usuario.getCredencialesUsuario();
        if (!credencialesUsuario.getPassword().equals(dtoIniciarSesion.getPassword())) {
            throw new ExcepcionInicioSesion("Credenciales Incorrectas");
        }
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public DTOUsuarioOut registrarUsuario(DTOUsuarioIn dtoUsuarioIn) {
        Usuario usuario = converterUsuario.dtoInAEntidad(dtoUsuarioIn);
        CredencialesUsuario credencialesUsuario = new CredencialesUsuario(dtoUsuarioIn.getPassword(), usuario);
        Equipo equipo = new Equipo(dtoUsuarioIn.getEquipo().getNombreEquipo(), dtoUsuarioIn.getEquipo().getLiga(), Collections.emptyList());
        LocalizacionClima localizacionClima = new LocalizacionClima(dtoUsuarioIn.getLocalizacionClima().getPais(), dtoUsuarioIn.getLocalizacionClima().getCodigoPostal(), Collections.emptyList());
        repositorioUsuario.save(usuario);
        usuario.setCredencialesUsuario(credencialesUsuario);
        repositorioCredenciales.save(credencialesUsuario);
        usuario.setEquipo(equipo);
        repositorioEquipo.save(equipo);
        usuario.setLocalizacionClima(localizacionClima);
        repositorioLocalizacionClima.save(localizacionClima);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public void enviarEmailCambioPassword(DTOEnvioCorreoIn dtoEnvioCorreoIn) {
        String emailUsuario = dtoEnvioCorreoIn.getEmailPara();
        // Buscamos al usuario por el email
        Usuario usuario = repositorioUsuario.findUsuarioByEmail(emailUsuario);
        // Comprobamos que exista y que el email no sea nulo
        if (Objects.nonNull(usuario) && Objects.nonNull(emailUsuario) && !emailUsuario.isEmpty()) {
            try {
                Context context = new Context();
                Map<String, Object> model = new HashMap<>();
                // Generamos los códigos de seguridad y los seteamos en el usuario
                String codigoVerificacion = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                usuario.setCodigoVerificacionCambioContrasenna(codigoVerificacion);
                usuario.setTokenSeguridad(UUID.randomUUID().toString().replace("-", ""));
                // Sustituimos las variables en la plantilla
                model.put("userName", usuario.getUsername());
                model.put("codVerificacion", codigoVerificacion);
                context.setVariables(model);
                // Enviamos el email y actualizamos el usuario
                serviceCorreosElectronicos.enviarCorreoElectronico(context, "recuperar-pass-email", dtoEnvioCorreoIn);
                repositorioUsuario.save(usuario);
            } catch (MessagingException e) {
                throw new ExcepcionCorreoElectronico("Error de red al enviar el correo");
            }
        } else {
            throw new ExcepcionCorreoElectronico("El correo electrónico no existe");
        }
    }

    @Override
    public void cambiarPassword(DTOCambioPasswordIn dtoCambioPasswordIn) {
        String tokenSeguridad = dtoCambioPasswordIn.getTokenSeguridad();
        Usuario usuario = repositorioUsuario.findUsuarioByTokenSeguridad(tokenSeguridad);
        if (Objects.nonNull(usuario) && Objects.nonNull(tokenSeguridad) && !tokenSeguridad.isEmpty()) {
            CredencialesUsuario credencialesUsuario = repositorioCredenciales.findCredencialesByIdUsuario(usuario);
            credencialesUsuario.setPassword(dtoCambioPasswordIn.getNuevaPassword());
            usuario.setTokenSeguridad(null);
            usuario.setCodigoVerificacionCambioContrasenna(null);
            repositorioUsuario.save(usuario);
            repositorioCredenciales.save(credencialesUsuario);
        } else {
            throw new ExcepcionCambiarContrasenna("El token a expirado");
        }
    }
}
