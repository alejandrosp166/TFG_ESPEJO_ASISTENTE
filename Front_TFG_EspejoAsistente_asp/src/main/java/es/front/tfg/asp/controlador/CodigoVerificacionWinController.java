package es.front.tfg.asp.controlador;

import es.front.tfg.asp.dtos.DTOEnvioCorreo;
import es.front.tfg.asp.servicio.iservice.IServiceAuth;
import es.front.tfg.asp.utils.Utiles;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class CodigoVerificacionWinController implements Initializable {
    @FXML
    private TextField fieldCodigo, fieldEmail;
    @FXML
    private Button btnVerificarCodigo, btnEmail;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private Utiles utiles;
    @Autowired
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

    public void enviarMail(ActionEvent e) {
        // llamar al servicio para enviar el mail
        serviceAuth.enviarMailRecuperacion(new DTOEnvioCorreo(fieldEmail.getText(), "nombre"));
        // AVISAR CUANDO SE HAGA
    }

    public void verificarCodigo(ActionEvent e) {
        // llama el servicio para verificar el código y cambia a la ventana de cambiar-pass
        cambiarVentana(e, getClass(), "/vistas/cambiar-pass.fxml");
    }

    private void cambiarVentana(ActionEvent e, Class<?> c, String resource) {
        cambioVentana = true;
        hiloCambioInterfaz.interrupt();
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
                            fieldEmail.setBorder(borde);
                            fieldEmail.requestFocus();
                        });
                        case 2 -> Platform.runLater(() -> {
                            btnEmail.setBorder(borde);
                            btnEmail.requestFocus();
                            if (mandoControllerGeneral.isConfirmarPulsado()) {
                                mandoControllerGeneral.setConfirmarPulsado(false);
                                btnEmail.fire();
                            }
                        });
                        case 3 -> Platform.runLater(() -> {
                            fieldCodigo.setBorder(borde);
                            fieldCodigo.requestFocus();
                        });
                        case 4 -> Platform.runLater(() -> {
                            btnVerificarCodigo.setBorder(borde);
                            btnVerificarCodigo.requestFocus();
                        });
                    }
                    Thread.sleep(100);
                }
                return null;
            }

            private void eliminarBordes() {
                Border borde = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3)));
                fieldCodigo.setBorder(borde);
                fieldEmail.setBorder(borde);
                btnEmail.setBorder(borde);
                btnVerificarCodigo.setBorder(borde);
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
