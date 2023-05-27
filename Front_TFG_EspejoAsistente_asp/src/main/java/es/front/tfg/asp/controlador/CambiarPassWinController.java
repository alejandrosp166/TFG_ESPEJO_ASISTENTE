package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOCambioPassword;
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
import java.util.ResourceBundle;
@Controller
public class CambiarPassWinController implements Initializable {
    @FXML
    private TextField fieldPassword1, fieldPassword2;
    @FXML
    private Button btnCambiarContrasenna;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private IServiceAuth serviceAuth;
    @Autowired
    private IServiceUsuario serviceUsuario;
    @Autowired
    private Utiles utiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        hiloCambiarInterfaz.setListaComponentes(cargarComponentes());
        utiles.iniciarHilos();
    }

    public void cambiarContrasenna(ActionEvent e) {
        String nuevaPass = fieldPassword1.getText();
        if (nuevaPass.equals(fieldPassword2.getText())) {
            serviceAuth.cambiarContrasenna(new DTOCambioPassword(utiles.obtenerElementoPropieades("token"), nuevaPass));
            cambiarVentana(e, getClass(), "/vistas/index.fxml");
        } else {
            // Las contraseñas no son iguales
        }
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldPassword1),
                Map.entry(2, fieldPassword2),
                Map.entry(3, btnCambiarContrasenna)
        );
    }
}
