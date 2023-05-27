package es.front.tfg.asp.utils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HiloCambiarInterfaz implements Runnable {
    @Autowired
    private HiloControlMando hiloControlMando;
    @Autowired
    private Utiles utiles;
    @Setter
    private Map<Integer, Node> listaComponentes = new HashMap<>();
    @Setter
    @Getter
    private boolean hiloIniciado = false;
    private int indexComboBox = 0;

    @Override
    @SneakyThrows
    public void run() {
        Border bordeAzul = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2)));
        Border bordeNegro = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2)));
        hiloIniciado = true;
        while (hiloIniciado) {
            if (listaComponentes.size() > 0) {
                int puntero = hiloControlMando.getPosicionPuntero();
                Node componente = listaComponentes.get(puntero);
                Platform.runLater(() -> cambiarBordeAndFocus(componente, bordeAzul));
                eliminarBordes(bordeNegro);
                Thread.sleep(100);
            }
        }
    }

    private void cambiarBordeAndFocus(Node componente, Border borde) {
        if (componente instanceof Button btn) {
            btn.setBorder(borde);
            comprobarPulsadoBoton(btn);
        } else if (componente instanceof TextField txt) {
            txt.setBorder(borde);
            comprobarPulsadoTextField(txt);
        } else if (componente instanceof ComboBox<?> cmb) {
            cmb.setBorder(borde);
            cambiarContenidoComboBox(cmb);
        } else if (componente instanceof CheckBox checkBox) {
            checkBox.setBorder(borde);
            comprobarPulsadoCheckBox(checkBox);
        }
        componente.requestFocus();
    }

    private void eliminarBordes(Border borde) {
        for (Node componente : listaComponentes.values()) {
            if (componente instanceof Button btn) {
                btn.setBorder(borde);
            } else if (componente instanceof TextField txt) {
                txt.setBorder(borde);
            } else if (componente instanceof ComboBox<?> cmb) {
                cmb.setBorder(borde);
            } else if (componente instanceof CheckBox checkBox) {
                checkBox.setBorder(borde);
            }
        }
    }

    private void comprobarPulsadoTextField(TextField text) {
        if (hiloControlMando.isBtnEquisPulsada()) {
            hiloControlMando.setBtnEquisPulsada(false);
            utiles.tecladoVirtual(text);
        }
    }

    private void comprobarPulsadoBoton(Button btn) {
        if (hiloControlMando.isBtnEquisPulsada()) {
            hiloControlMando.setBtnEquisPulsada(false);
            btn.fire();
        }
    }

    private void comprobarPulsadoCheckBox(CheckBox checkBox) {
        if (hiloControlMando.isBtnEquisPulsada()) {
            hiloControlMando.setBtnEquisPulsada(false);
            checkBox.setSelected(!checkBox.isSelected());
        }
    }

    private void cambiarContenidoComboBox(ComboBox<?> cmb) {
        if (hiloControlMando.isBtnTrianguloPulsado()) {
            hiloControlMando.setBtnTrianguloPulsado(false);
            if (indexComboBox <= cmb.getItems().size()) {
                indexComboBox++;
            } else {
                indexComboBox = 0;
            }
            cmb.getSelectionModel().select(indexComboBox);
        }
    }
}
