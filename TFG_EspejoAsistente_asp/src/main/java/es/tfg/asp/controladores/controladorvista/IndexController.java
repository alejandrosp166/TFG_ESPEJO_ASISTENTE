package es.tfg.asp.controladores.controladorvista;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class IndexController implements Initializable{
    @Autowired
    MandoControllerGeneral mandoControllerGeneral;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Holaaa");
    }
}
