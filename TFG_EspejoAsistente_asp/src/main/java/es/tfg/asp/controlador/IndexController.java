package es.tfg.asp.controlador;

import es.tfg.asp.servicio.iservice.ServiceCredenciales;
import es.tfg.asp.servicio.iservice.ServiceUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class IndexController implements Initializable{
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    @Autowired
    private ServiceCredenciales serviceCredenciales;
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * El método initialize en JavaFX se utiliza para inicializar un controlador de vista después de que se hayan establecido todos los objetos de la vista.
         * Este método se ejecuta automáticamente después de cargar la vista en la aplicación y se puede usar para inicializar cualquier objeto o configuración
         * necesaria en el controlador. Los parámetros URL y ResourceBundle se utilizan para obtener información sobre la ubicación de la vista y cualquier
         * recurso adicional que pueda ser necesario en el controlador.
         */
    }

    public void iniciarSesion(ActionEvent e) {
        if (serviceUsuario.validarUsuario("asd", "asda")) {
            // Se mete en la aplicación
        } else {
            // Credenciales incorrectas (VALIDACIONES DE DATOS EN SERVICE Y DEVUELVE UN MENSAJE)
        }
    }

    public void registrarse(ActionEvent e) {
        // Mandar a la página de registro
    }

    public void iniciarSesionFaceId(ActionEvent e) {

    }
}
