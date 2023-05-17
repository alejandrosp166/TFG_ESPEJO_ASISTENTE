package es.front.tfg.asp.controlador;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClimaWinController implements Initializable, Runnable {
    @FXML
    private Label lblHora, lblDiaMesAnio;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    private boolean cambioVentana;
    private Thread hiloCambioInterfaz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mandoControllerGeneral.setPosicionPuntero(1);
        mandoControllerGeneral.setConfirmarPulsado(false);
        cambioVentana = false;
        this.run();
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
