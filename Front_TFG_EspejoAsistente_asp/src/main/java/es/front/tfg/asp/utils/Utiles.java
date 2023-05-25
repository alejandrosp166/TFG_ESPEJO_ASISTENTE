package es.front.tfg.asp.utils;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

@Component
@Setter
public class Utiles {
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private TaskCambioInterfaz taskCambioInterfaz;
    private Thread hiloMandoController;
    private Thread hiloTaskCambioInterfaz;

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

    public <T, R> void llenarCombobox(List<T> listaObjetos, ComboBox<R> comboBox, Function<T, R> map) {
        ObservableList<R> observable = FXCollections.observableArrayList();
        for (T objeto : listaObjetos) {
            R nombre = map.apply(objeto);
            observable.add(nombre);
        }
        comboBox.setItems(observable);
    }

    public void guardarElementoPropiedades(String key, String contenido) {
        Properties properties = new Properties();
        properties.setProperty(key, contenido);
        try (OutputStream escribir = new FileOutputStream("session-config.properties")) {
            properties.store(escribir, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String obtenerElementoPropieades(String key) {
        String token = null;
        try (InputStream leer = new FileInputStream("session-config.properties")) {
            Properties properties = new Properties();
            properties.load(leer);
            token = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
