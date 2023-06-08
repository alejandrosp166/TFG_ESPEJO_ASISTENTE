package es.front.tfg.asp.utils;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponseLiga;
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
    private Stage stage;

    /**
     * Inicia los hilos de control de interfaz
     */
    public void iniciarHilos() {
        if (!hiloControlMando.isHiloIniciado() && !hiloCambiarInterfaz.isHiloIniciado()) {
            Thread hiloMandoController = new Thread(hiloControlMando);
            hiloMandoController.start();
            Thread hiloTaskCambioInterfaz = new Thread(hiloCambiarInterfaz);
            hiloTaskCambioInterfaz.start();
        }
    }

    /**
     * Cambia la ventana de la aplicación
     *
     * @param e        el evento del elemento que se accionó
     * @param c        la clase del elemento que se accinó
     * @param resource la vista a la que se quiere mover
     */
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

    /**
     * Crea un modal
     *
     * @param tituloModal    título del modal
     * @param contenidoTexto contenido del modal
     */
    public void crearModal(String tituloModal, String contenidoTexto) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(tituloModal);
        dialog.setContentText(contenidoTexto);
        dialog.initOwner(stage);
        dialog.showAndWait();
    }

    /**
     * Crea el teclado virtual
     *
     * @param txt el textfield en el que se quiere escribir
     */
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

    /**
     * Formatea la ventana del teclado virtual
     *
     * @param txt el textfield del teclado virtual
     * @return el teclado virtualz
     */
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

    /**
     * @param listaObjetos la lista de objetos a cargar
     * @param comboBox     el combobox en el que se quiere cargar los datos
     * @param map
     * @param <T>
     * @param <R>
     */
    public <T, R> void llenarCombobox(List<T> listaObjetos, ComboBox<R> comboBox, Function<T, R> map) {
        ObservableList<R> observable = FXCollections.observableArrayList();
        for (T objeto : listaObjetos) {
            R nombre = map.apply(objeto);
            observable.add(nombre);
        }
        comboBox.setItems(observable);
    }

    /**
     * Llena las listas de elementos y los formatea según la clase
     *
     * @param lista
     * @param <T>
     */
    public <T> void llenarListView(ListView<T> lista) {
        lista.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && Objects.nonNull(item)) {
                    switch (item.getClass().getSimpleName()) {
                        case "Clasificacion" -> {
                            ResponseLiga.Clasificacion responseLiga = (ResponseLiga.Clasificacion) item;
                            setText(responseLiga.getRank() + responseLiga.getTeam().getName());
                            ImageView imageView = new ImageView();
                            Image image = new Image(responseLiga.getTeam().getLogo(), true);
                            imageView.setImage(image);
                            imageView.setFitHeight(50);
                            imageView.setFitWidth(50);
                            setGraphic(imageView);
                        }
                        case "ResponseClima" -> {
                            ResponseClima clima = (ResponseClima) item;
                            setText(clima.getMain().getTemp() + "Cº " + clima.getMain().getHumidity() + " %");
                            ImageView imageView = new ImageView();
                            Image image = new Image("http://openweathermap.org/img/wn/" + clima.getWeather().get(0).getIcon() + ".png", true);
                            imageView.setImage(image);
                            setGraphic(imageView);
                        }
                    }
                }
            }
        });
    }

    /**
     * Obtiene el país del elementos en ingés
     *
     * @param paisEspannol el país en español
     * @return el país en ingés
     */
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

    /**
     * Obtiene el código del país
     *
     * @param pais el país
     * @return el código del país
     */
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

    /**
     * Obtiene el ID del país en la api footbal
     *
     * @param pais el país
     * @return el ID del país en la api footbal
     */
    public String obtenerIdPais(String pais) {
        String idPais = "";
        switch (pais.toLowerCase()) {
            case "españa" -> idPais = "140";
            case "inglaterra" -> idPais = "39";
            case "francia" -> idPais = "61";
            case "italia" -> idPais = "135";
            case "alemania" -> idPais = "78";
        }
        return idPais;
    }

    /**
     * Pasa del kelvin a grado centígrados
     *
     * @param kelvin la unidad en kelvin
     * @return la unidad en grados
     */
    public String pasarKelvinAGrados(String kelvin) {
        return Double.toString(Math.round(Double.parseDouble(kelvin) - 273.15));
    }

    /**
     * Pasa de metros por segundo a KM/H
     *
     * @param metrosPorSegundo los metros por segundo
     * @return los KM/H
     */
    public String pasarMetrosPorSegundosKilometrosPorHora(String metrosPorSegundo) {
        return Double.toString(Math.round(Double.parseDouble(metrosPorSegundo) * 3.6));
    }
}
