package es.front.tfg.asp.utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

@Component
@Setter
public class Utiles {
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private TaskCambioInterfaz taskCambioInterfaz;
    private static Thread hiloMandoController;
    private static Thread hiloTaskCambioInterfaz;

    public void iniciarHilos() {
        if (!mandoControllerGeneral.isIniciado() && !taskCambioInterfaz.isIniciado()) {
            Thread hiloMandoController = new Thread(mandoControllerGeneral);
            hiloMandoController.start();
            Thread hiloTaskCambioInterfaz = new Thread(taskCambioInterfaz);
            hiloTaskCambioInterfaz.start();
        }
    }

    public void cambiarVentanaAplicacion(ActionEvent e, Class<?> c, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(c.getResource(resource));
            loader.setControllerFactory(applicationContext::getBean);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 800, false, SceneAntialiasing.BALANCED);
            stage.setScene(scene);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void guardarTokenSeguridad(String token) {
        Properties properties = new Properties();
        properties.setProperty("token", token);
        try (OutputStream escribir = new FileOutputStream("session-config.properties")) {
            properties.store(escribir, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String obtenerTokenSeguridad() {
        String token = null;
        try (InputStream leer = new FileInputStream("session-config.properties")) {
            Properties properties = new Properties();
            properties.load(leer);
            token = properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
