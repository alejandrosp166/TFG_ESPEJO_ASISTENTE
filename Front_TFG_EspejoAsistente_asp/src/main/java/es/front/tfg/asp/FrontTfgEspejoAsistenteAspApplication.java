package es.front.tfg.asp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FrontTfgEspejoAsistenteAspApplication extends Application {
    public static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        applicationContext = SpringApplication.run(getClass());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load(), 800, 800, false, SceneAntialiasing.BALANCED);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(false);
        stage.show();
    }
}
