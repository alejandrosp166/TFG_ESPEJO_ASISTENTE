package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponseLiga;
import es.front.tfg.asp.modelo.response.ResponsePartido;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
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
public class EquipoWinController implements Initializable, Runnable {
    @FXML
    private ListView<ResponseLiga.Clasificacion> listClasificacion;
    @FXML
    private ImageView imgLocalAhora, imgVisitanteAhora, imgLocalAnterior, imgVisitanteAnterior;
    @FXML
    private Label lblHora, lblDiaMesAnio, lblResultadoHomeAhora, lblResultadoOutAhora, lblResultadoHomeAnterior, lblResultadoOutAnterior, lblUsername, lblMinutoAhora, lblMinutoAnterior;
    @FXML
    private Button btnConfiguracion, btnCerrarSesion, btnVolver;
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private HiloCambiarInterfaz hiloCambiarInterfaz;
    @Autowired
    private IServiceEquipo serviceEquipo;
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
        cargarClasificacion();
        cargarPartidosEnVivo();
    }

    /**
     * Carga la clasificación de la liga del usuario
     */
    private void cargarClasificacion() {
        String idLiga = utiles.obtenerIdPais(usuarioLogeado.getPaisLiga());
        List<ResponseLiga> clasificacion = serviceEquipo.obtenerClasificacionLiga(idLiga);
        if (clasificacion.size() > 0) {
            listClasificacion.setItems(FXCollections.observableArrayList(clasificacion.get(0).getLeague().getStandings().get(0)));
            utiles.llenarListView(listClasificacion);
        }
    }

    /**
     * Carga dos partidos aleatorios que se estén jugando en ese momento de la liga del usuario
     */
    private void cargarPartidosEnVivo() {
        String idLiga = utiles.obtenerIdPais(usuarioLogeado.getPaisLiga());
        List<ResponsePartido> listaPartidos = serviceEquipo.obtenerPartidosEnVivoLiga(idLiga);
        List<ResponseEquipo> equipoLocal;
        List<ResponseEquipo> equipoVisitante;
        if (listaPartidos.size() > 1) {
            int partidoAleatorio1 = (int) Math.floor(Math.random() * listaPartidos.size());
            int partidoAleatorio2;
            do {
                partidoAleatorio2 = (int) Math.floor(Math.random() * listaPartidos.size());
            } while (partidoAleatorio2 == partidoAleatorio1);

            equipoLocal = serviceEquipo.obtenerEquipoPorId(listaPartidos.get(partidoAleatorio1).getTeams().getHome().getId() + "");
            equipoVisitante = serviceEquipo.obtenerEquipoPorId(listaPartidos.get(partidoAleatorio1).getTeams().getAway().getId() + "");
            imgLocalAhora.setImage(new Image(equipoLocal.get(0).getTeam().getLogo(), true));
            imgVisitanteAhora.setImage(new Image(equipoVisitante.get(0).getTeam().getLogo(), true));

            lblResultadoOutAhora.setText(listaPartidos.get(partidoAleatorio1).getTeams().getHome().getGoals() + "");
            lblResultadoHomeAhora.setText(listaPartidos.get(partidoAleatorio1).getTeams().getAway().getGoals() + "");
            lblMinutoAhora.setText(listaPartidos.get(partidoAleatorio1).getFixture().getStatus().getSeconds());

            equipoLocal = serviceEquipo.obtenerEquipoPorId(listaPartidos.get(partidoAleatorio2).getTeams().getHome().getId() + "");
            equipoVisitante = serviceEquipo.obtenerEquipoPorId(listaPartidos.get(partidoAleatorio2).getTeams().getAway().getId() + "");
            imgLocalAnterior.setImage(new Image(equipoLocal.get(0).getTeam().getLogo(), true));
            imgVisitanteAnterior.setImage(new Image(equipoVisitante.get(0).getTeam().getLogo(), true));

            lblResultadoOutAnterior.setText(listaPartidos.get(partidoAleatorio2).getTeams().getHome().getGoals() + "");
            lblResultadoHomeAnterior.setText(listaPartidos.get(partidoAleatorio2).getTeams().getAway().getGoals() + "");
            lblMinutoAnterior.setText(listaPartidos.get(partidoAleatorio2).getFixture().getStatus().getSeconds());
        }
    }

    private void cargarDatosMiEquipo() {

    }

    /**
     * Carga los datos del usuario en la vista
     */
    private void cargarDatosUsuario() {
        usuarioLogeado = datos.obtenerUsuarioLogeado();
        lblUsername.setText(usuarioLogeado.getUsername());
    }

    /**
     * Cierra sesión en la aplicación
     *
     * @param e
     */
    public void cerrarSesion(ActionEvent e) {
        datos.cerrarSesion(e, getClass(), "/vistas/index.fxml");
    }

    /**
     * Mueve al usuario a la ventana configuración de la aplicación
     *
     * @param e
     */
    public void ventanaConfiguracion(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/configuracion.fxml");
    }

    /**
     * Carga los componentes de la interfaz en el mapper para que puedan ser controlados
     *
     * @return una lista mapper de componentes
     */
    public void ventanaClima(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/clima.fxml");
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
