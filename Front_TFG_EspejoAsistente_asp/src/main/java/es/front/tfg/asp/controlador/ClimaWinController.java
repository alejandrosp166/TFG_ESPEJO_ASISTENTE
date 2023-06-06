package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.servicio.iservice.IServiceClima;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import es.front.tfg.asp.utils.Datos;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.Utiles;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

@Controller
public class ClimaWinController implements Initializable, Runnable {
    @FXML
    private Label lblHora, lblDiaMesAnio, lblUsername, lblTemperatura, lblVelocidadViento, lblTempMax, lblTempMin, lblHumedad;
    @FXML
    private Button btnConfiguracion, btnCerrarSesion;
    @FXML
    private ImageView imgEstadoClima;
    @FXML
    private ListView<ResponseClima> listDatosProximosDias;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private IServiceClima serviceClima;
    @Autowired
    private Utiles utiles;
    @Autowired
    private Datos datos;
    private DTOUsuarioOut usuarioLogeado;
    private boolean cambioVentana;
    private Thread hiloCambioInterfaz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        cambioVentana = false;
        cargarDatosUsuario();
        cargarDatosClimaticos();
        this.run();
    }

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

    private void cargarDatosUsuario() {
        usuarioLogeado = datos.obtenerUsuarioLogeado();
        lblUsername.setText(usuarioLogeado.getUsername());
    }

    public void cerrarSesion(ActionEvent e) {
        datos.cerrarSesion(e, getClass(), "/vistas/index.fxml");
    }

    public void ventanaConfiguracion(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/configuracion.fxml");
    }

    public void ventanaEquipo(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/equipo.fxml");
    }

    @Override
    public void run() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (!cambioVentana) {
                    Platform.runLater(() -> {
                        LocalDateTime tiempo = LocalDateTime.now();
                        lblHora.setText(obtenerHoraActual(tiempo));
                        lblDiaMesAnio.setText(obtenerFechaActual(tiempo));
                    });
                    Thread.sleep(1000);
                }
                return null;
            }

            private String obtenerFechaActual(LocalDateTime tiempo) {
                String diaSemana = tiempo.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                int diaMes = tiempo.getDayOfMonth();
                String mes = tiempo.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                int anio = tiempo.getYear();
                return diaSemana + " " + diaMes + " " + mes + " " + anio;
            }

            private String obtenerHoraActual(LocalDateTime tiempo) {
                String amPm = "AM";
                int hora = tiempo.getHour();
                int minutos = tiempo.getMinute();
                if (tiempo.getHour() > 12) {
                    amPm = "PM";
                }
                return hora + " : " + minutos + " " + amPm;
            }
        };
        hiloCambioInterfaz = new Thread(task);
        hiloCambioInterfaz.start();
    }
}
