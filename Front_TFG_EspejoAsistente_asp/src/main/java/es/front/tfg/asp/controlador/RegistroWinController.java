package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import es.front.tfg.asp.modelo.dtos.DTOLocalizacionClima;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ApiResponse;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
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
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;

/**
 * Clase controlador Registro
 */
@Controller
public class RegistroWinController implements Initializable {
    @FXML
    private TextField fieldUsuario, fieldPassword, fieldNombre, fieldApellidos, fieldEmail, fieldCodigoPostal;
    @FXML
    private CheckBox checkEsAdmin;
    @FXML
    private Button btnCompletarRegistro, btnVolver;
    @FXML
    private ComboBox<String> cmbPaisClima, cmbEquipoFav, cmbLigaFav;
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
    }

    /**
     * Devuelve al usuario a la ventana anterior
     *
     * @param e
     */
    public void volver(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/index.fxml");
    }

    /**
     * Hace la petición de registro al backend
     *
     * @param e
     */
    public void completarRegistro(ActionEvent e) {
        DTOUsuarioIn dtoUsuarioIn = cargarUsuarioDatosVista();
        Object respuesta = serviceAuth.registrarUsuario(dtoUsuarioIn);
        if (respuesta instanceof DTOUsuarioOut) {
            utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/index.fxml");
        } else if (respuesta instanceof ApiResponse apiResponse) {
            utiles.crearModal("No se pudo completar el registro", apiResponse.getMensaje());
        }
    }

    /**
     * Obtiene el usuario que está cargado en la vista
     *
     * @return el usuario que está en la vista cargado
     */
    private DTOUsuarioIn cargarUsuarioDatosVista() {
        String username = fieldUsuario.getText();
        String password = fieldPassword.getText();
        String nombre = fieldNombre.getText();
        String apellidos = fieldApellidos.getText();
        String email = fieldEmail.getText();
        String codigoPostal = fieldCodigoPostal.getText();
        boolean admin = checkEsAdmin.isSelected();
        String paisLiga = cmbLigaFav.getValue();
        String pais = cmbPaisClima.getValue();
        String equipo = cmbEquipoFav.getValue();
        DTOLocalizacionClima dtoLocalizacionClima = new DTOLocalizacionClima(pais, codigoPostal);
        DTOEquipo dtoEquipo = new DTOEquipo(paisLiga, equipo);
        return new DTOUsuarioIn(null, username, nombre, apellidos, email, admin, password, null, null, dtoEquipo, dtoLocalizacionClima);
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
     * Carga los componentes de la interfaz en el mapper para que puedan ser controlados
     *
     * @return una lista mapper de componentes
     */
    private Map<Integer, Node> cargarComponentes() {
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldPassword),
                Map.entry(3, fieldNombre),
                Map.entry(4, fieldApellidos),
                Map.entry(5, fieldEmail),
                Map.entry(6, fieldCodigoPostal),
                Map.entry(7, cmbLigaFav),
                Map.entry(8, cmbEquipoFav),
                Map.entry(9, checkEsAdmin),
                Map.entry(10, cmbPaisClima),
                Map.entry(11, btnCompletarRegistro),
                Map.entry(12, btnVolver)
        );
    }
}
