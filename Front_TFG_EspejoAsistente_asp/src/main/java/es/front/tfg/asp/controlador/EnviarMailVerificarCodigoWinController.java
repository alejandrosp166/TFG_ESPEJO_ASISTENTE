package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.modelo.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.HiloCambiarInterfaz;
import es.front.tfg.asp.utils.Utiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private Utiles utiles;
    @Autowired
    private IServiceAuth serviceAuth;
    @Autowired
    private IServiceUsuario serviceUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<Integer, Node> map = cargarComponentes();
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        hiloCambiarInterfaz.setListaComponentes(map);
        utiles.iniciarHilos();
    }

    public void enviarMail(ActionEvent e) {
        serviceAuth.enviarMailRecuperacion(new DTOEnvioCorreo(fieldEmail.getText(), "nombre"));
    }

    public void verificarCodigo(ActionEvent e) {
        DTOUsuario usuario = serviceUsuario.obtenerUsuarioPorCodigoVerificacion(fieldCodigo.getText());
        if (Objects.nonNull(usuario)) {
            utiles.guardarElementoPropiedades("token",usuario.getTokenSeguridad());
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
