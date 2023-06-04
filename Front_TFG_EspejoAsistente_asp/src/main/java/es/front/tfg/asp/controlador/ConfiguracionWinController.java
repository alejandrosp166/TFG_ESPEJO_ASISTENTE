package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import es.front.tfg.asp.modelo.dtos.DTOLocalizacionClima;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.HiloCambiarInterfaz;
import es.front.tfg.asp.utils.Utiles;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
@Controller
public class ConfiguracionWinController implements Initializable {
    @FXML
    private TextField fieldUsuario, fieldNombre, fieldApellidos, fieldEmail, fieldCodigoPostal;
    @FXML
    private Button btnGuardarConfiguracion, btnVolver, btnEliminarCuenta;
    @FXML
    private CheckBox checkEsAdmin;
    @FXML
    private ComboBox<String> cmbLocalizacion, cmbEquipoFav, cmbLigaFav;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private IServiceUsuario serviceUsuario;
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
        cargarPaises();
        cargarDatosUsuarioLogeadoEnVista();
    }

    public void guardarConfig(ActionEvent e) {
        serviceUsuario.actualizarUsuario(obtenerDatosVista(), utiles.obtenerElementoPropieades("uuidUsuario"));
    }

    private DTOUsuarioIn obtenerDatosVista() {
        String username = fieldUsuario.getText();
        String nombre = fieldNombre.getText();
        String apellidos = fieldApellidos.getText();
        String email = fieldEmail.getText();
        boolean admin = checkEsAdmin.isSelected();
        String codigoPostal = cmbLocalizacion.getValue();
        String pais = cmbLigaFav.getValue();
        String equipo = cmbEquipoFav.getValue();
        DTOEquipo dtoEquipo  = new DTOEquipo(pais, equipo);
        DTOLocalizacionClima dtoLocalizacionClima = new DTOLocalizacionClima(pais, codigoPostal);
        return new DTOUsuarioIn(null, username, nombre, apellidos, email, admin, null, null, null, dtoEquipo, dtoLocalizacionClima);
    }

    private void cargarDatosUsuarioLogeadoEnVista() {
        DTOUsuarioOut usuario = serviceUsuario.obtenerUsuarioPorUuid(utiles.obtenerElementoPropieades("uuidUsuario"));
        if (Objects.nonNull(usuario)) {
            fieldUsuario.setText(usuario.getUsername());
            fieldNombre.setText(usuario.getNombre());
            fieldApellidos.setText(usuario.getApellidos());
            fieldEmail.setText(usuario.getEmail());
            checkEsAdmin.setSelected(usuario.isEsAdmin());
            fieldCodigoPostal.setText(usuario.getCodigoPostal());
            cmbLigaFav.setValue(usuario.getPais());
            cmbEquipoFav.setValue(usuario.getEquipoFav());
        }
    }

    public void activarCmbBoxEquipo(ActionEvent e) {
        String pais = cmbLigaFav.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(pais)) {
            cargarDatosComboBoxEquipo(utiles.traducirPaisIngles(pais));
        }
    }

    private void cargarPaises() {
        cmbLigaFav.setItems(FXCollections.observableArrayList("España", "Inglaterra", "Alemania", "Italia", "Francia"));
    }

    private void cargarDatosComboBoxEquipo(String pais) {
        utiles.llenarCombobox(serviceEquipo.obtenerEquiposPorPais(pais), cmbEquipoFav, equipo -> equipo.getTeam().getName());
    }

    public void volver(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/clima.fxml");
    }
    
    public void eliminarCuenta(ActionEvent e) {

    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldNombre),
                Map.entry(3, fieldApellidos),
                Map.entry(4, fieldEmail),
                Map.entry(5, checkEsAdmin),
                Map.entry(6, cmbLocalizacion),
                Map.entry(7, cmbEquipoFav),
                Map.entry(8, btnGuardarConfiguracion),
                Map.entry(9, btnVolver)
        );
    }
}
