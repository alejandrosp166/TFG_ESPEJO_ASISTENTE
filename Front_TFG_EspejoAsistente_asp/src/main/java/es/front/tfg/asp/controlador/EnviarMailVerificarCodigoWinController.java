package es.front.tfg.asp.controlador;

import es.front.tfg.asp.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.MandoControllerGeneral;
import es.front.tfg.asp.utils.TaskCambioInterfaz;
import es.front.tfg.asp.utils.Utiles;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
public class EnviarMailVerificarCodigoWinController implements Initializable {
    @FXML
    private TextField fieldCodigo, fieldEmail;
    @FXML
    private Button btnVerificarCodigo, btnEmail;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private TaskCambioInterfaz taskCambioInterfaz;
    @Autowired
    private Utiles utiles;
    @Autowired
    private IServiceAuth serviceAuth;
    @Autowired
    private IServiceUsuario serviceUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mandoControllerGeneral.setPosicionPuntero(1);
        mandoControllerGeneral.setConfirmarPulsado(false);
        taskCambioInterfaz.setListaComponentes(cargarComponentes());
        utiles.iniciarHilos();
    }

    public void enviarMail(ActionEvent e) {
        serviceAuth.enviarMailRecuperacion(new DTOEnvioCorreo(fieldEmail.getText(), "nombre"));
    }

    public void verificarCodigo(ActionEvent e) {
        DTOUsuario usuario = serviceUsuario.obtenerUsuarioPorCodigoVerificacion(fieldCodigo.getText());
        if (Objects.nonNull(usuario)) {
            utiles.guardarTokenSeguridad(usuario.getTokenSeguridad());
            cambiarVentana(e, getClass(), "/vistas/cambiar-pass.fxml");
        } else {
            // AVISAR AL USUARIO DE QUE NO EXISTE ESE CÓDIGO
        }
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldEmail),
                Map.entry(2, btnEmail),
                Map.entry(3, fieldCodigo),
                Map.entry(4, btnVerificarCodigo)
        );
    }
}