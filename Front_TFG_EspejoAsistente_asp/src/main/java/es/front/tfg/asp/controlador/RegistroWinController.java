package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import es.front.tfg.asp.modelo.dtos.DTOLocalizacionClima;
import es.front.tfg.asp.modelo.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.HiloCambiarInterfaz;
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
import org.springframework.scheduling.annotation.Async;
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
    private TextField fieldUsuario, fieldPassword, fieldNombre, fieldApellidos, fieldEmail, fieldCodigoPostal;
    @FXML
    private CheckBox checkEsAdmin;
    @FXML
    private Button btnCompletarRegistro, btnVolver;
    @FXML
    private ComboBox<String> cmbLocalizacion, cmbEquipoFav;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private IServiceAuth serviceAuth;
    @Autowired
    private IServiceEquipo serviceEquipo;
    @Autowired
    private Utiles utiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<Integer, Node> map = cargarComponentes();
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        hiloCambiarInterfaz.setListaComponentes(map);
        utiles.iniciarHilos();
        cargarEquiposCmb();
    }

    public void volver(ActionEvent e) {
        cambiarVentana(e, getClass(), "/vistas/index.fxml");
    }

    public void completarRegistro(ActionEvent e) {
        DTOUsuario dtoUsuario = cargarUsuarioDatosVista();
        serviceAuth.registrarUsuario(dtoUsuario);
        cambiarVentana(e, getClass(), "/vistas/index.fxml");
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    private DTOUsuario cargarUsuarioDatosVista() {
        String username = fieldUsuario.getText();
        String password = fieldPassword.getText();
        String nombre = fieldNombre.getText();
        String apellidos = fieldApellidos.getText();
        String email = fieldEmail.getText();
        String codigoPostal = fieldCodigoPostal.getText();
        boolean admin = checkEsAdmin.isSelected();
        String pais = cmbLocalizacion.getValue();
        String equipo = cmbEquipoFav.getValue();
        DTOLocalizacionClima dtoLocalizacionClima = new DTOLocalizacionClima(pais, codigoPostal);
        DTOEquipo dtoEquipo = new DTOEquipo("liga", equipo);
        return new DTOUsuario(null, username, nombre, apellidos, email, admin, password, null, null, dtoEquipo, dtoLocalizacionClima);
    }

    private void cargarEquiposCmb() {
        utiles.llenarCombobox(serviceEquipo.obtenerEquiposLigaSantander(), cmbEquipoFav, equipo -> equipo.getTeam().getName());
    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldPassword),
                Map.entry(3, fieldNombre),
                Map.entry(4, fieldApellidos),
                Map.entry(5, fieldEmail),
                Map.entry(6, fieldCodigoPostal),
                Map.entry(7, checkEsAdmin),
                Map.entry(8, cmbEquipoFav),
                Map.entry(9, cmbLocalizacion),
                Map.entry(10, btnCompletarRegistro),
                Map.entry(11, btnVolver)
        );
    }
}
