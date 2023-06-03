package es.back.tfg.asp.servicio.serviceimpl;

import es.back.tfg.asp.modelo.dto.in.DTOEnvioCorreoIn;
import es.back.tfg.asp.servicio.iservice.IServiceCorreosElectronicos;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class ServiceCorreosElectronicosImpl implements IServiceCorreosElectronicos {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String emailRemitente;
    @Override
    public void enviarCorreoElectronico(Context contextPlantilla, String nombrePlantilla, DTOEnvioCorreoIn dtoEnvioCorreoIn) throws MessagingException {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
        String html = templateEngine.process(nombrePlantilla, contextPlantilla);
        helper.setText(html, true);
        helper.setFrom(emailRemitente);
        helper.setTo(dtoEnvioCorreoIn.getEmailPara());
        javaMailSender.send(mensaje);
    }
}
