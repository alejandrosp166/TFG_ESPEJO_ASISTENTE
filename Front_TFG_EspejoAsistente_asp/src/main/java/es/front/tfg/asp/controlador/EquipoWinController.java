package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponseLiga;
import es.front.tfg.asp.modelo.response.ResponsePartido;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
import es.front.tfg.asp.utils.Datos;
import es.front.tfg.asp.utils.Utiles;
import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class EquipoWinController implements Initializable {
    @FXML
    private ListView<ResponseLiga.Clasificacion> listClasificacion;
    @FXML
    private ImageView imgLocalAhora, imgVisitanteAhora, imgLocalAnterior, imgVisitanteAnterior;
    @FXML
    private Label lblResultadoHomeAhora, lblResultadoOutAhora, lblResultadoHomeAnterior, lblResultadoOutAnterior, lblUsername, lblMinutoAhora, lblMinutoAnterior;
    @FXML
    private Button btnConf, btnCerrarSesion;
    @Autowired
    private IServiceEquipo serviceEquipo;
    @Autowired
    private Utiles utiles;
    @Autowired
    private Datos datos;
    private DTOUsuarioOut usuarioLogeado;
    private int idEquipo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarDatosUsuario();
        cargarClasificacion();
        cargarPartidosEnVivo();
    }

    private void cargarClasificacion() {
        String idLiga = utiles.obtenerIdPais(usuarioLogeado.getPaisLiga());
        List<ResponseLiga.Clasificacion> clasificacion = serviceEquipo.obtenerClasificacionLiga(idLiga).get(0).getLeague().getStandings().get(0);
        listClasificacion.setItems(FXCollections.observableArrayList(clasificacion));
        utiles.llenarListView(listClasificacion);
        for (ResponseLiga.Clasificacion clas : clasificacion) {
            if (clas.getTeam().getName().equals(usuarioLogeado.getEquipoFav())) {
                idEquipo = clas.getTeam().getId();
            }
        }
    }

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

    public void ventanaClima(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/clima.fxml");
    }
}
