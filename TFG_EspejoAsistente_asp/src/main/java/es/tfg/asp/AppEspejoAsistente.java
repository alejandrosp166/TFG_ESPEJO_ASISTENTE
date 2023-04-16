package es.tfg.asp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppEspejoAsistente extends Application {

	public static ConfigurableApplicationContext applicationContext;
	public static Parent rootNode;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		applicationContext = SpringApplication.run(getClass());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/index.fxml"));
		loader.setControllerFactory(applicationContext::getBean);
		Scene scene = new Scene(loader.load(), 600, 600, false, SceneAntialiasing.BALANCED);
		stage.setScene(scene);
		//stage.setFullScreen(true);
		stage.show();
	}
}
