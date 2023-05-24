package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    //@FXML
    //private ImageView imgLogoPrincipal;
    @FXML
    private Label lblErrorUsername, lblErrorPassword;
    @FXML
    private TextField fieldUsuario, fieldPassword;
    @FXML
    private Button btnIniciarSesion, btnIniciarSesionFaceId, btnRegistro, btnOlvidarContrasenna;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private TaskCambioInterfaz taskCambioInterfaz;
    @Autowired
    private IServiceUsuario serviceUsuario;
    @Autowired
    private Utiles utiles;

    /**
     * El método initialize en JavaFX se utiliza para inicializar un controlador de vista después de que se hayan establecido todos los objetos de la vista.
     * Este método se ejecuta automáticamente después de cargar la vista en la aplicación y se puede usar para inicializar cualquier objeto o configuración
     * necesaria en el controlador. Los parámetros URL y ResourceBundle se utilizan para obtener información sobre la ubicación de la vista y cualquier
     * recurso adicional que pueda ser necesario en el controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mandoControllerGeneral.setPosicionPuntero(1);
        mandoControllerGeneral.setConfirmarPulsado(false);
        taskCambioInterfaz.setListaComponentes(cargarComponentes());
        utiles.iniciarHilos();
    }

    public void iniciarSesion(ActionEvent e) {
        DTOUsuario usuario = serviceUsuario.obtenerUsuarioPorUsername(fieldUsuario.getText());
        if(true) {
            utiles.guardarElementoPropiedades("uuidUsuario", usuario.getUuid());
            cambiarVentana(e, getClass(), "/vistas/clima.fxml");
        }
    }

    public void registrarse(ActionEvent e) {
        cambiarVentana(e, getClass(), "/vistas/registro.fxml");
    }

    public void olvidarContrasenna(ActionEvent e) {
        cambiarVentana(e, getClass(), "/vistas/enviar-mail.fxml");
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    public void iniciarSesionFaceId(ActionEvent e) {

    }

    private Map<Integer, Node> cargarComponentes() {
        return Map.ofEntries(
                Map.entry(1, fieldUsuario),
                Map.entry(2, fieldPassword),
                Map.entry(3, btnOlvidarContrasenna),
                Map.entry(4, btnIniciarSesion),
                Map.entry(5, btnIniciarSesionFaceId),
                Map.entry(6, btnRegistro)
        );
    }
}
