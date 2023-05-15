package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.converters.ConverterUsuario;
import es.back.tfg.asp.modelo.dto.in.DTOCambioPasswordIn;
import es.back.tfg.asp.modelo.dto.in.DTOEnvioCorreoIn;
import es.back.tfg.asp.modelo.dto.in.DTOUsuarioIn;
import es.back.tfg.asp.modelo.dto.out.DTOUsuarioOut;
import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import es.back.tfg.asp.repositorio.RepositorioCredenciales;
import es.back.tfg.asp.repositorio.RepositorioUsuario;
import es.back.tfg.asp.servicio.iservice.IServiceAuth;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class ServiceAuthImpl implements IServiceAuth {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioCredenciales repositorioCredenciales;
    @Autowired
    private ConverterUsuario converterUsuario;
    @Value("${spring.mail.username}")
    private String emailRemitente;

    @Override
    public DTOUsuarioOut registrarUsuario(DTOUsuarioIn dtoUsuarioIn) {
        Usuario usuario = converterUsuario.dtoInAEntidad(dtoUsuarioIn);
        CredencialesUsuario credencialesUsuario = new CredencialesUsuario(dtoUsuarioIn.getPassword(), usuario);
        repositorioUsuario.save(usuario);
        usuario.setCredencialesUsuario(credencialesUsuario);
        repositorioCredenciales.save(credencialesUsuario);
        return converterUsuario.entidadADTOOut(usuario);
    }

    @Override
    public void enviarEmailCambioPassword(DTOEnvioCorreoIn dtoEnvioCorreoIn) {
        String emailUsuario = dtoEnvioCorreoIn.getEmailPara();
        String usernameUsuario = dtoEnvioCorreoIn.getUsername();
        // Buscamos al usuario por el email
        Usuario usuario = repositorioUsuario.findUsuarioByUsername(usernameUsuario);
        // Comprobamos que exista y que el email no sea nulo
        if (Objects.nonNull(usuario) && Objects.nonNull(emailUsuario) && !emailUsuario.isEmpty()) {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
                Context context = new Context();
                Map<String, Object> model = new HashMap<>();
                // Generamos los códigos de seguridad y los seteamos en el usuario
                String codigoVerificacion = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                usuario.setCodigoVerificacionCambioContrasenna(codigoVerificacion);
                usuario.setTokenSeguridad(UUID.randomUUID().toString().replace("-", ""));
                // Sustituimos las variables en la plantilla
                model.put("userName", usernameUsuario);
                model.put("url", emailUsuario);
                model.put("codVerificacion", codigoVerificacion);
                context.setVariables(model);
                // Generamos la plantilla de tymeleaf
                String htmlTemplate = templateEngine.process("recuperar-pass-email", context);
                helper.setFrom(emailRemitente);
                helper.setTo(emailUsuario);
                helper.setText(htmlTemplate, true);
                // Enviamos el mail con la plantilla y actualizamos el usuario
                javaMailSender.send(mensaje);
                repositorioUsuario.save(usuario);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
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
            repositorioUsuario.save(usuario);
            repositorioCredenciales.save(credencialesUsuario);
        }
    }
}
