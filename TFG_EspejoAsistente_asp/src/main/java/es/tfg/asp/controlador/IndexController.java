package es.tfg.asp.controlador;

import es.tfg.asp.servicio.iservice.ServiceUsuario;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase controlador Index
 */
@Controller
public class IndexController implements Initializable, Runnable {
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
    @Autowired
    private ServiceUsuario serviceUsuario;

    /**
     * El método initialize en JavaFX se utiliza para inicializar un controlador de vista después de que se hayan establecido todos los objetos de la vista.
     * Este método se ejecuta automáticamente después de cargar la vista en la aplicación y se puede usar para inicializar cualquier objeto o configuración
     * necesaria en el controlador. Los parámetros URL y ResourceBundle se utilizan para obtener información sobre la ubicación de la vista y cualquier
     * recurso adicional que pueda ser necesario en el controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread hiloMando = new Thread(mandoControllerGeneral);
        hiloMando.start();
        this.run();
    }

    @Override
    public void run() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Border borde = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3)));
                while (true) {
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
                            btnIniciarSesion.setBorder(borde);
                            btnIniciarSesion.requestFocus();
                        });
                        case 4 -> Platform.runLater(() -> {
                            btnIniciarSesionFaceId.setBorder(borde);
                            btnIniciarSesionFaceId.requestFocus();
                        });
                        case 5 -> Platform.runLater(() -> {
                            btnRegistro.setBorder(borde);
                            btnRegistro.requestFocus();
                        });
                    }
                    Thread.sleep(100);
                }
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
        Thread hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.setDaemon(true);
        hiloCambioInterfaz.start();
    }

    /**
     * Valida el usuario según los datos que haya en el login
     *
     * @param e
     */
    public void iniciarSesion(ActionEvent e) {
        // Guardamos los datos del usuario
        String username = fieldUsuario.getText();
        String password = fieldPassword.getText();
        // Validamos los datos del usuario
        if (serviceUsuario.validarUsuario(username, password)) {
            System.out.println("Entro");
        } else {
            System.out.println("No entro");
        }
    }

    /**
     * @param e
     */
    public void iniciarSesionFaceId(ActionEvent e) {

    }

    /**
     * Accede a la página de registro
     *
     * @param e
     */
    public void registrarse(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/registro.fxml"));
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
