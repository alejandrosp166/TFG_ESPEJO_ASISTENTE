package es.front.tfg.asp.controlador;

import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class ConfiguracionWinController implements Initializable {
    @FXML
    private TextField fieldUsuario, fieldNombre, fieldApellidos, fieldEmail;
    @FXML
    private Button btnRegistroRostro, btnCompletarRegistro;
    @FXML
    private CheckBox checkEsAdmin;
    @FXML
    private ComboBox<?> cmbLocalizacion, cmbEquipoFav;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private TaskCambioInterfaz taskCambioInterfaz;
    @Autowired
    private IServiceUsuario serviceUsuario;
    @Autowired
    private Utiles utiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarDatosUsuarioLogeado();
        mandoControllerGeneral.setPosicionPuntero(1);
        mandoControllerGeneral.setConfirmarPulsado(false);
        taskCambioInterfaz.setListaComponentes(cargarComponentes());
        utiles.iniciarHilos();
    }

    public void guardarConfig(ActionEvent e) {
        serviceUsuario.actualizarUsuario(cargarUsuarioVista());
    }

    private DTOUsuario cargarUsuarioVista() {
        String username = fieldUsuario.getText();
        String password = ""; // TIENE QUE SER OTRO DTO
        String nombre = fieldNombre.getText();
        String apellidos = fieldApellidos.getText();
        String email = fieldEmail.getText();
        boolean admin = checkEsAdmin.isSelected();
        // String ciudad = cmbLocalizacion.getValue().toString();
        // String equipo = cmbEquipoFav.getValue().toString();
        return new DTOUsuario(username, nombre, apellidos, email, admin, password, null, null);
    }

    private void cargarDatosUsuarioLogeado() {
        DTOUsuario usuario = serviceUsuario.obtenerUsuarioPorUsername("");
        if (Objects.nonNull(usuario)) {
            fieldUsuario.setText(usuario.getUsername());
            fieldNombre.setText(usuario.getNombre());
            fieldApellidos.setText(usuario.getApellidos());
            fieldEmail.setText(usuario.getEmail());
            checkEsAdmin.setSelected(usuario.isAdmin());
            // cmbEquipoFav
            // cmbLocalizacion
        }
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldNombre),
                Map.entry(3, fieldApellidos),
                Map.entry(4, fieldEmail),
                Map.entry(5, btnRegistroRostro),
                Map.entry(6, checkEsAdmin),
                Map.entry(7, cmbLocalizacion),
                Map.entry(8, cmbEquipoFav),
                Map.entry(9, btnCompletarRegistro)
        );
    }
}
