package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.servicio.iservice.IServiceClima;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.Datos;
import es.front.tfg.asp.utils.HiloCambiarInterfaz;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.Utiles;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class ClimaWinController implements Initializable, Runnable {
    @FXML
    private Label lblHora, lblDiaMesAnio, lblUsername, lblTemperatura, lblVelocidadViento, lblTempMax, lblTempMin, lblHumedad;
    @FXML
    private Button btnConfiguracion, btnCerrarSesion, btnVolver;
    @FXML
    private ImageView imgEstadoClima;
    @FXML
    private ListView<ResponseClima> listDatosProximosDias;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private IServiceClima serviceClima;
    @Autowired
    private Utiles utiles;
    @Autowired
    private Datos datos;
    private DTOUsuarioOut usuarioLogeado;
    private boolean cambioVentana;
    private Thread hiloCambioInterfaz;

    /**
     * Se ejecuta cuando se carga la vista
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarComponentes();
        Map<Integer, Node> map = cargarComponentes();
        hiloCambiarInterfaz.setListaComponentes(map);
        cargarDatosUsuario();
        cargarDatosClimaticos();
        this.run();
    }

    /**
     * Carga todos los datos climáticos
     */
    private void cargarDatosClimaticos() {
        String codigoPais = utiles.obtenerCodigoPais(usuarioLogeado.getPaisClima());
        String codigoPostal = usuarioLogeado.getCodigoPostal();

        ResponseClima clima = serviceClima.obtenerDatosClimaticosActualesPorCodigoPostalPais(codigoPostal, codigoPais);
        lblTemperatura.setText(clima.getMain().getTemp() + " ºC");
        lblVelocidadViento.setText(utiles.pasarMetrosPorSegundosKilometrosPorHora(clima.getWind().getSpeed()) + " Km/H");
        lblTempMax.setText("MAX " + clima.getMain().getTemp_max() + " ºC ");
        lblTempMin.setText("MIN " + clima.getMain().getTemp_min() + " ºC ");
        lblHumedad.setText("HUMEDAD " + clima.getMain().getHumidity() + " %");
        imgEstadoClima.setImage(new Image("http://openweathermap.org/img/wn/" + clima.getWeather().get(0).getIcon() + "@4x.png", true));

        List<ResponseClima> listaClima = serviceClima.obtenerDatosClimaticosProximosDiasPorCodigoPostalPais(codigoPostal, codigoPais);
        listDatosProximosDias.setItems(FXCollections.observableArrayList(listaClima));
        utiles.llenarListView(listDatosProximosDias);
    }

    /**
     * Carga los datos del usuario en la vista
     */
    private void cargarDatosUsuario() {
        usuarioLogeado = datos.obtenerUsuarioLogeado();
        lblUsername.setText(usuarioLogeado.getUsername());
    }

    /**
     * Cierra sesión en la cuenta del usuario
     *
     * @param e
     */
    public void cerrarSesion(ActionEvent e) {
        datos.cerrarSesion(e, getClass(), "/vistas/index.fxml");
    }

    /**
     * Mueve al usuario a la ventana de configuración
     *
     * @param e
     */
    public void ventanaConfiguracion(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/configuracion.fxml");
    }

    /**
     * Mueve al usuario a la ventana equipo
     *
     * @param e
     */
    public void ventanaEquipo(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/equipo.fxml");
    }

    private Map<Integer, Node> cargarComponentes() {
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        cambioVentana = false;
        return Map.ofEntries(
                Map.entry(1, btnVolver),
                Map.entry(2, btnConfiguracion),
                Map.entry(3, btnCerrarSesion)
        );
    }

    /**
     * Muestra la hora del sistema en la aplicación
     */
    @Override
    public void run() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (!cambioVentana) {
                    Platform.runLater(() -> {
                        LocalDateTime tiempo = LocalDateTime.now();
                        lblHora.setText(utiles.obtenerHoraActual(tiempo));
                        lblDiaMesAnio.setText(utiles.obtenerFechaActual(tiempo));
                    });
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
