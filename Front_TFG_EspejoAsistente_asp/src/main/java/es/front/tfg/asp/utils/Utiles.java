package es.front.tfg.asp.utils;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
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
    @Autowired
    private IServiceUsuario serviceUsuario;
    private Stage stage;
    private DTOUsuarioOut usuarioLogeado;

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

    public void cerrarSesion(ActionEvent e, Class<?> c, String resource) {
        cambiarVentanaAplicacion(e, c, resource);
    }

    public void crearModal(String tituloModal, String contenidoTexto) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(tituloModal);
        dialog.setContentText(contenidoTexto);
        dialog.initOwner(stage);
        dialog.showAndWait();
    }

    public void crearTecladoVirtual(TextField txt) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Teclado Virtual");
        GridPane teclado = obtenerTecladoVirtual(txt);
        dialog.getDialogPane().setContent(teclado);
        // dialog.setY(txt.getLayoutY() + txt.getPrefHeight() * 2);
        // dialog.setX(txt.getLayoutX() - 25);
        dialog.initOwner(stage);
        dialog.showAndWait();
    }

    private GridPane obtenerTecladoVirtual(TextField txt) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPrefWidth(txt.getPrefWidth());
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        String[][] teclasTeclado = {
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "<----"},
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "BORRAR"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"},
                {"Z", "X", "C", "V", "B", "N", "M", "CERRAR"}
        };
        int indiceBotones = 0;
        Map<Integer, Node> listaComponentes = new HashMap<>();
        Map<Integer, Node> listaComponentesAnterior = hiloCambiarInterfaz.getListaComponentes();
        for (int row = 0; row < teclasTeclado.length; row++) {
            for (int col = 0; col < teclasTeclado[row].length; col++) {
                String tecla = teclasTeclado[row][col];
                Button boton = new Button(tecla);
                boton.setStyle("-fx-font-size: 16px; -fx-pref-width: 100px; -fx-pref-height: 50px;");
                switch (tecla) {
                    case "<----" -> {
                        if (!txt.getText().equals("")) {
                            boton.setOnAction(event -> txt.setText(txt.getText().substring(0, txt.getText().length() - 1)));
                        }
                    }
                    case "BORRAR" -> boton.setOnAction(event -> txt.setText(""));
                    case "CERRAR" -> boton.setOnAction(event -> {
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                        hiloCambiarInterfaz.setListaComponentes(listaComponentesAnterior);
                        hiloControlMando.setPosicionPuntero(1);
                    });
                    default -> boton.setOnAction(event -> txt.setText(txt.getText() + tecla));
                }

                gridPane.add(boton, col, row);
                listaComponentes.put(++indiceBotones, boton);
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

    public <T> void llenarListView(ListView<T> lista) {
        lista.setCellFactory(param -> new ListCell<T>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && Objects.nonNull(item)) {
                    switch (item.getClass().getSimpleName()) {
                        case "ResponseEquipo" -> {
                            ResponseEquipo equipo = (ResponseEquipo) item;
                            setText(equipo.getTeam().getName() + " - Posición: " + equipo.getTeam().getName());
                            Image image = new Image(equipo.getTeam().getLogo(), true);
                            imageView.setImage(image);
                            imageView.setFitWidth(40);
                            imageView.setFitHeight(40);
                            setGraphic(imageView);
                        }
                        case "ResponseClima" -> {
                            ResponseClima clima = (ResponseClima) item;
                            setText(clima.getMain().getTemp() + "Cº " + clima.getMain().getHumidity() + " %");
                            Image image = new Image("http://openweathermap.org/img/wn/" + clima.getWeather().get(0).getIcon() + ".png", true);
                            imageView.setImage(image);
                            setGraphic(imageView);
                        }
                    }
                }
            }
        });
    }

    public DTOUsuarioOut obtenerUsuarioLogeado() {
        if (Objects.isNull(usuarioLogeado)) {
            usuarioLogeado = serviceUsuario.obtenerUsuarioPorUuid(obtenerElementoPropieades("uuidUsuario"));
        }
        return usuarioLogeado;
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
        String propiedad = null;
        try (InputStream leer = new FileInputStream("session-config.properties")) {
            Properties properties = new Properties();
            properties.load(leer);
            propiedad = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propiedad;
    }

    public void borrarPropiedades() {

    }

    public String traducirPaisIngles(String paisEspannol) {
        String paisIngles = "";
        switch (paisEspannol.toLowerCase()) {
            case "españa" -> paisIngles = "spain";
            case "inglaterra" -> paisIngles = "england";
            case "francia" -> paisIngles = "france";
            case "italia" -> paisIngles = "italy";
            case "alemania" -> paisIngles = "germany";
        }
        return paisIngles;
    }

    public String obtenerCodigoPais(String pais) {
        String codigoPais = "";
        switch (pais.toLowerCase()) {
            case "españa" -> codigoPais = "es";
            case "inglaterra" -> codigoPais = "uk";
            case "francia" -> codigoPais = "fr";
            case "italia" -> codigoPais = "it";
            case "alemania" -> codigoPais = "de";
        }
        return codigoPais;
    }

    public String pasarKelvinAGrados(String kelvin) {
        return Double.toString(Math.round(Double.parseDouble(kelvin) - 273.15));
    }

    public String pasarMetrosPorSegundosKilometrosPorHora(String metrosPorSegundo) {
        return Double.toString(Math.round(Double.parseDouble(metrosPorSegundo) * 3.6));
    }
}
