package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponsePartido;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
import es.front.tfg.asp.utils.Utiles;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
    private ListView<ResponseEquipo> listClasificacion;
    @FXML
    private ImageView imgLocalAhora, imgVisitanteAhora;
    @FXML
    private Label lblResultadoHomeAhora, lblResultadoOutAhora;
    @FXML
    private Button btnConf, btnCerrarSesion;
    @Autowired
    private IServiceEquipo serviceEquipo;
    @Autowired
    private Utiles utiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listClasificacion.setItems(FXCollections.observableArrayList(serviceEquipo.obtenerEquiposLigaSantander()));
        utiles.llenarListView(listClasificacion);
    }

    public void cerrarSesion(ActionEvent e) {
        utiles.cerrarSesion(e, getClass(), "/vistas/index.fxml");
    }

    public void ventanaConfiguracion(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/configuracion.fxml");
    }

    public void ventanaClima(ActionEvent e) {
        utiles.cambiarVentanaAplicacion(e, getClass(), "/vistas/clima.fxml");
    }
}
