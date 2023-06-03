package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.servicio.iservice.IServiceClima;
import es.front.tfg.asp.utils.HiloControlMando;
import es.front.tfg.asp.utils.Utiles;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Label lblHora, lblDiaMesAnio, lblTemperatura, lblVelocidadViento, lblTempMax, lblTempMin, lblHumedad;
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
    private boolean cambioVentana;
    private Thread hiloCambioInterfaz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hiloControlMando.setPosicionPuntero(1);
        hiloControlMando.setBtnEquisPulsada(false);
        cambioVentana = false;
        cargarDatosClimaticos();
        this.run();
    }

    private void cargarDatosClimaticos() {
        ResponseClima clima = serviceClima.obtenerDatosClimaticosActualesPorCodigoPostal("");
        lblTemperatura.setText(utiles.pasarKelvinAGrados(clima.getMain().getTemp()) + " ºC");
        lblVelocidadViento.setText(utiles.pasarMetrosPorSegundosKilometrosPorHora(clima.getWind().getSpeed()) + " Km/H");
        lblTempMax.setText(utiles.pasarKelvinAGrados(clima.getMain().getTemp_max()) + " ºC");
        lblTempMin.setText(utiles.pasarKelvinAGrados(clima.getMain().getTemp_min()) + " ºC");
        lblHumedad.setText(clima.getMain().getHumidity() + " %");
        imgEstadoClima.setImage(new Image("http://openweathermap.org/img/wn/" + clima.getWeather().get(0).getIcon() + "@2x.png", true));
    }

    private void cargarDatosClimaticosLista() {
        listDatosProximosDias.setItems(FXCollections.observableArrayList(serviceClima.obtenerDatosClimaticosActualesPorCodigoPostal("")));
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
