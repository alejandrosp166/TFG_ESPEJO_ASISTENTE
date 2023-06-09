package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import es.front.tfg.asp.modelo.dtos.DTOLocalizacionClima;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ApiResponse;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.Datos;
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
import java.util.List;
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
    private ComboBox<String> cmbPaisClima, cmbEquipoFav, cmbLigaFav;
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
    @Autowired
    private Datos datos;

    /**
     * Se ejecuta cuando se carga la vista
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<Integer, Node> map = cargarComponentes();
        hiloCambiarInterfaz.setListaComponentes(map);
        utiles.iniciarHilos();
        cargarPaises();
        cargarDatosUsuarioLogeadoEnVista();
    }

    /**
     * Actualiza el usuario con la nueva configuración
     *
     * @param e
     */
    public void guardarConfig(ActionEvent e) {
        Object respuesta = serviceUsuario.actualizarUsuario(obtenerDatosVista(), datos.obtenerElementoPropieades("uuidUsuario"));
        if (respuesta instanceof DTOUsuarioOut) {
            datos.cerrarSesion(e, getClass(), "/vistas/index.fxml");
        } else if (respuesta instanceof ApiResponse apiResponse) {
            utiles.crearModal("Error al configurar al usuario", apiResponse.getMensaje());
        }
    }

    /**
     * Obtiene los datos que hay en la vista cargados
     *
     * @return el usuario que hay en la vista cargado
     */
    private DTOUsuarioIn obtenerDatosVista() {
        String username = fieldUsuario.getText();
        String nombre = fieldNombre.getText();
        String apellidos = fieldApellidos.getText();
        String email = fieldEmail.getText();
        boolean admin = checkEsAdmin.isSelected();
        String codigoPostal = fieldCodigoPostal.getText();
        String paisLiga = cmbLigaFav.getValue();
        String equipo = cmbEquipoFav.getValue();
        String paisLocalizacion = cmbPaisClima.getValue();
        DTOEquipo dtoEquipo = new DTOEquipo(paisLiga, equipo);
        DTOLocalizacionClima dtoLocalizacionClima = new DTOLocalizacionClima(paisLocalizacion, codigoPostal);
        return new DTOUsuarioIn(null, username, nombre, apellidos, email, admin, null, dtoEquipo, dtoLocalizacionClima);
    }

    /**
     * Carga al usuario logueado en la vista
     */
    private void cargarDatosUsuarioLogeadoEnVista() {
        Object respuesta = serviceUsuario.obtenerUsuarioPorUuid(datos.obtenerElementoPropieades("uuidUsuario"));
        if (respuesta instanceof DTOUsuarioOut usuario) {
            fieldUsuario.setText(usuario.getUsername());
            fieldNombre.setText(usuario.getNombre());
            fieldApellidos.setText(usuario.getApellidos());
            fieldEmail.setText(usuario.getEmail());
            checkEsAdmin.setSelected(usuario.isEsAdmin());
            fieldCodigoPostal.setText(usuario.getCodigoPostal());
            cmbLigaFav.setValue(usuario.getPaisLiga());
            cmbPaisClima.setValue(usuario.getPaisClima());
            cmbEquipoFav.setValue(usuario.getEquipoFav());
        } else if (respuesta instanceof ApiResponse apiResponse) {
            utiles.crearModal("Error al cargar datos del usuario", apiResponse.getMensaje());
        }
    }

    /**
     * Ejecuta un evento que carga los equipos del pais seleccionado en el combobox
     *
     * @param e
     */
    public void activarCmbBoxEquipo(ActionEvent e) {
        String pais = cmbLigaFav.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(pais)) {
            cargarDatosComboBoxEquipo(utiles.traducirPaisIngles(pais));
        }
    }

    /**
     * Carga los paises y ligas disponibles
     */
    private void cargarPaises() {
        List<String> listaPaises = List.of("España", "Inglaterra", "Alemania", "Italia", "Francia");
        cmbLigaFav.setItems(FXCollections.observableArrayList(listaPaises));
        cmbPaisClima.setItems(FXCollections.observableArrayList(listaPaises));
    }

    /**
     * Carga los equipos de una liga en el combobox
     *
     * @param pais el pais de la liga que se quiere cargar
     */
    private void cargarDatosComboBoxEquipo(String pais) {
        utiles.llenarCombobox(serviceEquipo.obtenerEquiposPorPais(pais), cmbEquipoFav, equipo -> equipo.getTeam().getName());
    }

    /**
     * Hace que el usuario pueda volver a la ventana anterior
     *
     * @param e
     */
    public void volver(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/clima.fxml");
    }

    /**
     * Elimina la cuenta del usuario
     *
     * @param e
     */
    public void eliminarCuenta(ActionEvent e) {
        serviceUsuario.eliminarUsuario(datos.obtenerUsuarioLogeado().getUuid());
    }

    /**
     * Carga los componentes de la interfaz en el mapper para que puedan ser controlados
     *
     * @return una lista mapper de componentes
     */
    private Map<Integer, Node> cargarComponentes() {
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldNombre),
                Map.entry(3, fieldApellidos),
                Map.entry(4, fieldEmail),
                Map.entry(5, fieldCodigoPostal),
                Map.entry(6, cmbLigaFav),
                Map.entry(7, cmbEquipoFav),
                Map.entry(8, checkEsAdmin),
                Map.entry(9, cmbPaisClima),
                Map.entry(10, btnGuardarConfiguracion),
                Map.entry(11, btnVolver),
                Map.entry(12, btnEliminarCuenta)
        );
    }
}
