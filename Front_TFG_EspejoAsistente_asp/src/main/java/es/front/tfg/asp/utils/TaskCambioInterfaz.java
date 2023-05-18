package es.front.tfg.asp.utils;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TaskCambioInterfaz {
    boolean asd = false;
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    private Map<Integer, Node> mapaComponentes;

    public void taskCambioInterfaz(List<Node> componentes) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Border borde = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(3)));
                while (!asd) {
                    eliminarBordes();
                    if (mapaComponentes.containsKey(mandoControllerGeneral.getPosicionPuntero())) {
                        Node componente = mapaComponentes.get(mandoControllerGeneral.getPosicionPuntero());
                        Platform.runLater(() -> {
                            if (componente instanceof Button) {
                                cambiarBordeAndFocus(componente, Button.class);
                            }
                        });
                    }
                }
                return null;
            }

            private void cambiarBordeAndFocus(Node componente, Class<?> clase) {
                Border borde = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(3)));
                ((Button) componente).setBorder(borde);
            }

            private void eliminarBordes() {
                Border borde = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3)));
                for (Node componente : componentes) {
                    if (componente instanceof Button) {
                        ((Button) componente).setBorder(borde);
                    } else if (componente instanceof TextField) {
                        ((TextField) componente).setBorder(borde);
                    }
                }
            }
        };
    }
}
