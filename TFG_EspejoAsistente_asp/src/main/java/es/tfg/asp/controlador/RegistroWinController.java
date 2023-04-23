package es.tfg.asp.controlador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroWinController implements Initializable, Runnable {
    @FXML
    private ImageView imgConfirmFaceId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgConfirmFaceId.setImage(new Image("E:\\ProyectosIntelliJ\\TFG_ESPEJO_ASISTENTE\\TFG_EspejoAsistente_asp\\src\\main\\resources\\img\\tickVerde.png"));
    }
    @Override
    public void run() {

    }
}
