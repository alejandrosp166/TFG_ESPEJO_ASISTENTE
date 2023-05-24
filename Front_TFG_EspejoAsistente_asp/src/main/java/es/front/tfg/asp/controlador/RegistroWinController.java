package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.utils.MandoControllerGeneral;
import es.front.tfg.asp.utils.TaskCambioInterfaz;
import es.front.tfg.asp.utils.Utiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Clase controlador Registro
 */
@Controller
public class RegistroWinController implements Initializable {
    @FXML
    private ImageView imgConfirmFaceId;
    @FXML
    private TextField fieldUsuario, fieldPassword, fieldNombre, fieldApellidos, fieldEmail;
    @FXML
    private CheckBox checkEsAdmin;
    @FXML
    private Button btnRegistroRostro, btnCompletarRegistro, btnVolver;
    @FXML
    private ComboBox cmbLocalizacion, cmbEquipoFav;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private TaskCambioInterfaz taskCambioInterfaz;
    @Autowired
    private IServiceAuth serviceAuth;
    @Autowired
    private Utiles utiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mandoControllerGeneral.setPosicionPuntero(1);
        mandoControllerGeneral.setConfirmarPulsado(false);
        taskCambioInterfaz.setListaComponentes(cargarComponentes());
        utiles.iniciarHilos();
    }

    public void volver(ActionEvent e) {
        cambiarVentana(e, getClass(), "/vistas/index.fxml");
    }

    public void completarRegistro(ActionEvent e) {
        DTOUsuario dtoUsuario = cargarUsuarioDatosVista();
        serviceAuth.registrarUsuario(dtoUsuario);
    }

    private DTOUsuario cargarUsuarioDatosVista() {
        String username = fieldUsuario.getText();
        String password = fieldPassword.getText();
        String nombre = fieldNombre.getText();
        String apellidos = fieldApellidos.getText();
        String email = fieldEmail.getText();
        boolean admin = checkEsAdmin.isSelected();
        // String ciudad = cmbLocalizacion.getValue().toString();
        // String equipo = cmbEquipoFav.getValue().toString();
        return new DTOUsuario(null, username, nombre, apellidos, email, admin, password, null, null);
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldPassword),
                Map.entry(3, fieldNombre),
                Map.entry(4, fieldApellidos),
                Map.entry(5, fieldEmail),
                Map.entry(6, btnRegistroRostro),
                Map.entry(7, checkEsAdmin),
                Map.entry(8, cmbLocalizacion),
                Map.entry(9, cmbEquipoFav),
                Map.entry(10, btnCompletarRegistro)
        );
    }
}
