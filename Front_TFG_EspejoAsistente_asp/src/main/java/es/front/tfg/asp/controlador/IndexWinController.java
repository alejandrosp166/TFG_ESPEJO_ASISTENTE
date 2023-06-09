package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOIniciarSesion;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioIn;
import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ApiResponse;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.Datos;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.HiloCambiarInterfaz;
import es.front.tfg.asp.utils.Utiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Clase controlador Index
 */
@Controller
public class IndexWinController implements Initializable {
    @FXML
    private Label lblErrorUsername, lblErrorPassword;
    @FXML
    private TextField fieldUsuario, fieldPassword;
    @FXML
    private Button btnIniciarSesion, btnIniciarSesionFaceId, btnRegistro, btnOlvidarContrasenna;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private IServiceUsuario serviceUsuario;
    @Autowired
    private IServiceAuth serviceAuth;
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
        // Cargamos los componentes para el control
        Map<Integer, Node> map = cargarComponentes();
        hiloCambiarInterfaz.setListaComponentes(map);
        // Iniciamos los hilos de control
        utiles.iniciarHilos();
    }

    /**
     * Inicia sesión en la aplicación
     *
     * @param e
     */
    public void iniciarSesion(ActionEvent e) {
        DTOIniciarSesion iniciarSesion = new DTOIniciarSesion(fieldUsuario.getText(), fieldPassword.getText());
        Object respuesta = serviceAuth.iniciarSesion(iniciarSesion);
        if (respuesta instanceof DTOUsuarioOut usuario) {
            datos.guardarElementoPropiedades("uuidUsuario", usuario.getUuid());
            utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/clima.fxml");
        } else if (respuesta instanceof ApiResponse apiResponse) {
            utiles.crearModal("No se pudo iniciar sesión", apiResponse.getMensaje());
        }
    }

    /**
     * Mueve al usuario al formulario de registro
     *
     * @param e
     */
    public void registrarse(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/registro.fxml");
    }

    /**
     * Mueve al usuario al formulario para recuperar la contraseña
     *
     * @param e
     */
    public void olvidarContrasenna(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/enviar-mail.fxml");
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
                Map.entry(3, btnIniciarSesion),
                Map.entry(4, btnRegistro),
                Map.entry(5, btnOlvidarContrasenna)
        );
    }
}
