package es.front.tfg.asp.controlador;

import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase controlador Registro
 */
@Controller
public class RegistroWinController implements Initializable {
    @FXML
    private ImageView imgConfirmFaceId;
    @FXML
    private TextField fieldUsuario;
    @FXML
    private TextField fieldNombre;
    @FXML
    private TextField fieldApellidos;
    @FXML
    private TextField fieldEmail;
    @FXML
    private CheckBox checkEsAdmin;
    @FXML
    private TextField fieldPassword;
    @FXML
    private Button btnRegistroRostro;
    @FXML
    private Button btnCompletarRegistro;
    @FXML
    private ComboBox cmbLocalizacion;
    @FXML
    private ComboBox cmbEquipoFav;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private IServiceAuth serviceUsuario;
    private boolean cambiarVentana;
    private Thread hiloCambioInterfaz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cambiarVentana = false;
        // imgConfirmFaceId.setImage(new Image("img/tickVerde.png"));
        taskCambioInterfaz();
    }

    public void completarRegistro(ActionEvent e) {
        DTOUsuario dtoUsuario = cargarUsuarioDatosVista();
        serviceUsuario.registrarUsuario(dtoUsuario);
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
        return new DTOUsuario(username, nombre, apellidos, email, admin, password);
    }

    private void interrumpirHilo() {
        hiloCambioInterfaz.interrupt();
    }

    private void taskCambioInterfaz() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Border borde = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(3)));
                while (!cambiarVentana) {
                    eliminarBordes();
                    switch (mandoControllerGeneral.getPosicionPuntero()) {
                        case 1 -> Platform.runLater(() -> {
                            fieldUsuario.setBorder(borde);
                            fieldUsuario.requestFocus();
                        });
                        case 2 -> Platform.runLater(() -> {
                            fieldPassword.setBorder(borde);
                            fieldPassword.requestFocus();
                        });
                        case 3 -> Platform.runLater(() -> {
                            fieldNombre.setBorder(borde);
                            fieldNombre.requestFocus();
                        });
                        case 4 -> Platform.runLater(() -> {
                            fieldApellidos.setBorder(borde);
                            fieldApellidos.requestFocus();
                        });
                        case 5 -> Platform.runLater(() -> {
                            fieldEmail.setBorder(borde);
                            fieldEmail.requestFocus();
                        });
                        case 6 -> Platform.runLater(() -> {
                            btnRegistroRostro.setBorder(borde);
                            btnRegistroRostro.requestFocus();
                        });
                        case 7 -> Platform.runLater(() -> {
                            checkEsAdmin.setBorder(borde);
                            checkEsAdmin.requestFocus();
                        });
                        case 8 -> Platform.runLater(() -> {
                            cmbLocalizacion.setBorder(borde);
                            cmbLocalizacion.requestFocus();
                        });
                        case 9 -> Platform.runLater(() -> {
                            cmbEquipoFav.setBorder(borde);
                            cmbEquipoFav.requestFocus();
                        });
                        case 10 -> Platform.runLater(() -> {
                            btnCompletarRegistro.setBorder(borde);
                            btnCompletarRegistro.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnCompletarRegistro.fire();
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
                fieldNombre.setBorder(borde);
                fieldApellidos.setBorder(borde);
                checkEsAdmin.setBorder(borde);
                fieldEmail.setBorder(borde);
                cmbEquipoFav.setBorder(borde);
                cmbLocalizacion.setBorder(borde);
                btnRegistroRostro.setBorder(borde);
                btnCompletarRegistro.setBorder(borde);
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
