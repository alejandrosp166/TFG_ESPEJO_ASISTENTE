package es.front.tfg.asp.controlador;

import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
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
    private Label lblErrorUsername, lblErrorPassword;
    @FXML
    private TextField fieldUsuario, fieldPassword;
    @FXML
    private Button btnIniciarSesion, btnIniciarSesionFaceId, btnRegistro, btnOlvidarContrasenna;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private IServiceEquipo serviceEquipo;
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
        // LLAMAR AL SERVICE DE LOGIN
        cambiarVentana(e, getClass(), "/vistas/clima.fxml");
    }

    public void registrarse(ActionEvent e) {
        cambiarVentana(e, getClass(), "/vistas/registro.fxml");
    }

    public void olvidarContrasenna(ActionEvent e) {
        cambiarVentana(e, getClass(), "/vistas/enviar-mail.fxml");
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        cambioVentana = true;
        hiloCambioInterfaz.interrupt();
        utiles.cambiarVentanaAplicacion(e, c, resource);
    }

    public void iniciarSesionFaceId(ActionEvent e) {

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
                            btnOlvidarContrasenna.setBorder(borde);
                            btnOlvidarContrasenna.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnOlvidarContrasenna.fire();
                            }
                        });
                        case 4 -> Platform.runLater(() -> {
                            btnIniciarSesion.setBorder(borde);
                            btnIniciarSesion.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnIniciarSesion.fire();
                            }
                        });
                        case 5 -> Platform.runLater(() -> {
                            btnIniciarSesionFaceId.setBorder(borde);
                            btnIniciarSesionFaceId.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnIniciarSesionFaceId.fire();
                            }
                        });
                        case 6 -> Platform.runLater(() -> {
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
                btnOlvidarContrasenna.setBorder(borde);
                btnIniciarSesion.setBorder(borde);
                btnIniciarSesionFaceId.setBorder(borde);
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
