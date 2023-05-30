package es.front.tfg.asp.controlador;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
@Controller
public class EquipoWinController implements Initializable {
    @FXML
    private ListView<DTOEquipo> listClasificacion;
    @FXML
    private Button btnConf, btnCerrarSesion;
    @Autowired
    private IServiceEquipo serviceEquipo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listClasificacion.setItems(FXCollections.observableArrayList(serviceEquipo.obtenerEquiposLigaSantander()));
        listClasificacion.setCellFactory(param -> new ListCell<DTOEquipo>() {
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(DTOEquipo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getTeam().getName() + " - Posición: " + item.getTeam().getCode());
                    Image image = new Image(item.getTeam().getLogo(), true);
                    imageView.setImage(image);
                    imageView.setFitWidth(40); // Establece el ancho deseado
                    imageView.setFitHeight(40);
                    setGraphic(imageView);
                }
            }
        });

    }
}
