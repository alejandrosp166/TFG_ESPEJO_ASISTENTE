package es.front.tfg.asp.controlador;

import es.front.tfg.asp.dtos.DTOCambioPassword;
import es.front.tfg.asp.dtos.DTOUsuario;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CambiarPassWinController implements Initializable {
    @FXML
    private TextField fieldPassword1, fieldPassword2;
    @FXML
    private Button btnCambiarContrasenna;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    private IServiceAuth serviceAuth;
    private boolean cambioVentana;
    private Thread hiloCambioInterfaz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mandoControllerGeneral.setPosicionPuntero(1);
        mandoControllerGeneral.setConfirmarPulsado(false);
        cambioVentana = false;
        taskCambioInterfaz();
    }

    public void cambiarContrasenna(ActionEvent e) {
        String nuevaPass = fieldPassword1.getText();
        if (nuevaPass.equals(fieldPassword2.getText())) {
            serviceAuth.cambiarContrasenna(new DTOCambioPassword("token", nuevaPass));
        } else {
            // Las contraseñas no son iguales
        }
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
                            fieldPassword1.setBorder(borde);
                            fieldPassword1.requestFocus();
                        });
                        case 2 -> Platform.runLater(() -> {
                            fieldPassword2.setBorder(borde);
                            fieldPassword2.requestFocus();
                        });
                        case 3 -> Platform.runLater(() -> {
                            btnCambiarContrasenna.setBorder(borde);
                            btnCambiarContrasenna.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnCambiarContrasenna.fire();
                            }
                        });
                    }
                    Thread.sleep(100);
                }
                return null;
            }

            private void eliminarBordes() {
                Border borde = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3)));
                fieldPassword2.setBorder(borde);
                fieldPassword1.setBorder(borde);
                btnCambiarContrasenna.setBorder(borde);
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
