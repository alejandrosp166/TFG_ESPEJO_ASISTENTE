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
    @Getter
    private Map<Integer, Node> listaComponentes = new HashMap<>();
    @Setter
    @Getter
    private boolean hiloIniciado = false;
    private int indexComboBox = 0;

    /**
     * Permite movernos entre los elementos de la lista de componentes
     */
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

    /**
     * Cambia la posición del elementos en el que nos encontramos
     *
     * @param componente el componente
     * @param borde      el borde que simboliza la posición en la cual nos encontramos
     */
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

    /**
     * Elimina los bordes para movernos a otro elemento
     *
     * @param borde el borde que simboliza que ya no estamos en ese elemento
     */
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

    /**
     * Comprueba si hemos pulsado X sobre el textfield
     *
     * @param text el textfield
     */
    private void comprobarPulsadoTextField(TextField text) {
        if (hiloControlMando.isBtnEquisPulsada()) {
            hiloControlMando.setBtnEquisPulsada(false);
            utiles.crearTecladoVirtual(text);
        }
    }

    /**
     * Comprueba si hemos pulsado el botón
     *
     * @param btn el botón
     */
    private void comprobarPulsadoBoton(Button btn) {
        if (hiloControlMando.isBtnEquisPulsada()) {
            hiloControlMando.setBtnEquisPulsada(false);
            btn.fire();
        }
    }

    /**
     * Comprueba si se ha pulsado el checkbox
     *
     * @param checkBox el checkbox
     */
    private void comprobarPulsadoCheckBox(CheckBox checkBox) {
        if (hiloControlMando.isBtnEquisPulsada()) {
            hiloControlMando.setBtnEquisPulsada(false);
            checkBox.setSelected(!checkBox.isSelected());
        }
    }

    /**
     * Mueve el elemento que hay dentro del combobox seleccionado a otro
     *
     * @param cmb el combox
     */
    private void cambiarContenidoComboBox(ComboBox<?> cmb) {
        if (hiloControlMando.isBtnTrianguloPulsado()) {
            hiloControlMando.setBtnTrianguloPulsado(false);
            if (indexComboBox < cmb.getItems().size()) {
                indexComboBox++;
            } else {
                indexComboBox = 0;
            }
            cmb.getSelectionModel().select(indexComboBox);
        }
    }

    /**
     * Cambia la lista de componentes que se está usando
     *
     * @param listaComponentes la lista de componentes
     */
    public void setListaComponentes(Map<Integer, Node> listaComponentes) {
        hiloControlMando.setLimitePuntero(listaComponentes.size());
        this.listaComponentes = listaComponentes;
    }
}
