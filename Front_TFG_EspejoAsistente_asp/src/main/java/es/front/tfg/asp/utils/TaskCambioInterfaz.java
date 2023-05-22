package es.front.tfg.asp.utils;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaskCambioInterfaz implements Runnable {
    @Autowired
    private MandoControllerGeneral mandoControllerGeneral;
    private Map<Integer, Node> listaComponentes = new HashMap<>();
    private boolean iniciado = false;


    @Override
    @SneakyThrows
    public void run() {
        Border bordeAzul = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(3)));
        Border bordeNegro = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(3)));
        iniciado = true;
        while (iniciado) {
            if (listaComponentes.size() > 0) {
                int puntero = mandoControllerGeneral.getPosicionPuntero();
                Node componente = listaComponentes.get(puntero);
                Platform.runLater(() -> cambiarBordeAndFocus(componente, bordeAzul));
                eliminarBordes(bordeNegro);
                Thread.sleep(100);
            }
        }
    }

    private void comprobarPulsado(Button btn) {
        if (mandoControllerGeneral.isConfirmarPulsado()) {
            btn.fire();
        }
    }

    private void cambiarBordeAndFocus(Node componente, Border borde) {
        if (componente instanceof Button btn) {
            btn.setBorder(borde);
            comprobarPulsado(btn);
        } else if (componente instanceof TextField) {
            ((TextField) componente).setBorder(borde);
        } else if (componente instanceof ComboBox<?>) {
            ((ComboBox<?>) componente).setBorder(borde);
        } else if (componente instanceof CheckBox) {
            ((CheckBox) componente).setBorder(borde);
        }
        componente.requestFocus();
    }

    private void eliminarBordes(Border borde) {
        for (Node componente : listaComponentes.values()) {
            if (componente instanceof Button) {
                ((Button) componente).setBorder(borde);
            } else if (componente instanceof TextField) {
                ((TextField) componente).setBorder(borde);
            } else if (componente instanceof ComboBox<?>) {
                ((ComboBox<?>) componente).setBorder(borde);
            } else if (componente instanceof CheckBox) {
                ((CheckBox) componente).setBorder(borde);
            }
        }
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public void setListaComponentes(Map<Integer, Node> listaComponentes) {
        this.listaComponentes = listaComponentes;
    }
}
