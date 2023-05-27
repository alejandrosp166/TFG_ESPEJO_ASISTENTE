package es.front.tfg.asp.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

@Component
@Setter
public class Utiles {
    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    private Thread hiloMandoController;
    private Thread hiloTaskCambioInterfaz;
    private Stage stage;

    public void iniciarHilos() {
        if (!hiloControlMando.isHiloIniciado() && !hiloCambiarInterfaz.isHiloIniciado()) {
            Thread hiloMandoController = new Thread(hiloControlMando);
            hiloMandoController.start();
            Thread hiloTaskCambioInterfaz = new Thread(hiloCambiarInterfaz);
            hiloTaskCambioInterfaz.start();
        }
    }

    public void cambiarVentanaAplicacion(ActionEvent e, Class<?> c, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(c.getResource(resource));
            loader.setControllerFactory(applicationContext::getBean);
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 800, false, SceneAntialiasing.BALANCED);
            stage.setScene(scene);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void tecladoVirtual(TextField txt) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Teclado Virtual");
        GridPane teclado = crearTecladoVirtual(txt);
        dialog.getDialogPane().setContent(teclado);
        dialog.initOwner(stage);
        dialog.showAndWait();
    }

    private GridPane crearTecladoVirtual(TextField txt) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        String[][] keys = {
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "<----"},
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "BORRAR TODO"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"},
                {"Z", "X", "C", "V", "B", "N", "M"}
        };
        int indiceBotones = 0;
        Map<Integer, Node> listaComponentes = new HashMap<>();
        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[row].length; col++) {
                String key = keys[row][col];
                Button button = new Button(key);
                button.setStyle("-fx-font-size: 16px; -fx-pref-width: 60px; -fx-pref-height: 40px;");
                if (key.equals("<----")) {
                    button.setOnAction(event -> txt.setText(txt.getText().substring(0, txt.getText().length() - 1)));
                } else if (key.equals("BORRAR TODO")) {
                    button.setOnAction(event -> txt.setText(""));
                } else {
                    button.setOnAction(event -> txt.setText(txt.getText() + key));
                }
                gridPane.add(button, col, row);
                listaComponentes.put(++indiceBotones, button);
            }
        }
        hiloCambiarInterfaz.setListaComponentes(listaComponentes);
        return gridPane;
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
