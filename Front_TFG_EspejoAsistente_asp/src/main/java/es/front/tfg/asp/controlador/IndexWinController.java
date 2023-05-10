package es.front.tfg.asp.controlador;

import es.front.tfg.asp.utils.Utiles;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase controlador Index
 */
@Controller
public class IndexWinController implements Initializable {
    //@FXML
    //private ImageView imgLogoPrincipal;
    @FXML
    private Label lblErrorUsername;
    @FXML
    private Label lblErrorPassword;
    @FXML
    private TextField fieldUsuario;
    @FXML
    private TextField fieldPassword;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnIniciarSesionFaceId;
    @FXML
    private Button btnRegistro;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    // @Autowired
    // private ServiceUsuario serviceUsuario;
    @Autowired
    private Utiles utiles;
    private boolean cambioVentana;
    private Thread hiloCambioInterfaz;

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
        cambioVentana = false;
        Thread hiloMando = new Thread(mandoControllerGeneral);
        hiloMando.start();
        taskCambioInterfaz();
    }

    public void iniciarSesion(ActionEvent e) {
        // Guardamos los datos del usuario
        String username = fieldUsuario.getText();
        String password = fieldPassword.getText();
        // Validamos los datos del usuario
        // serviceUsuario.validarUsuario(username, password)
        if (true) {
            hiloCambioInterfaz.interrupt();
            cambiarVentana(e, getClass(), "/vistas/clima.fxml");
        } else {
            lblErrorUsername.setText("No Inicio Sesión");
        }
    }

    public void iniciarSesionFaceId(ActionEvent e) {

    }

    public void registrarse(ActionEvent e) {
        hiloCambioInterfaz.interrupt();
        cambiarVentana(e, getClass(), "/vistas/registro.fxml");
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        cambioVentana = true;
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    private void taskCambioInterfaz() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Border borde = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(3)));
                while (!cambioVentana) {
                    eliminarBordes();
                    switch (mandoControllerGeneral.getPosicionPuntero()) {
                        case 1 -> Platform.runLater(() -> {
                            fieldUsuario.setBorder(borde);
                            fieldUsuario.requestFocus();
                            // Añadir que abra el "TECLADO"
                        });
                        case 2 -> Platform.runLater(() -> {
                            fieldPassword.setBorder(borde);
                            fieldPassword.requestFocus();
                            // Añadir que abra el "TECLADO"
                        });
                        case 3 -> Platform.runLater(() -> {
                            btnIniciarSesion.setBorder(borde);
                            btnIniciarSesion.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnIniciarSesion.fire();
                            }
                        });
                        case 4 -> Platform.runLater(() -> {
                            btnIniciarSesionFaceId.setBorder(borde);
                            btnIniciarSesionFaceId.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnIniciarSesionFaceId.fire();
                            }
                        });
                        case 5 -> Platform.runLater(() -> {
                            btnRegistro.setBorder(borde);
                            btnRegistro.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnRegistro.fire();
                            }
                        });
                    }
                    Thread.sleep(100);
                }
                return null;
            }

            private void eliminarBordes() {
                Border borde = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3)));
                fieldUsuario.setBorder(borde);
                fieldPassword.setBorder(borde);
                btnRegistro.setBorder(borde);
                btnIniciarSesion.setBorder(borde);
                btnIniciarSesionFaceId.setBorder(borde);
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
