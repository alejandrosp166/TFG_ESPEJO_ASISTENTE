package es.front.tfg.asp.utils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import org.springframework.stereotype.Controller;

/**
 * Esta clase permite moverse al usuario por la interfaz
 */
@Data
@Controller
public class HiloControlMando implements Runnable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private net.java.games.input.Controller ps4Input;
    private int posicionPuntero = 1;
    private int limitePuntero;
    private boolean btnEquisPulsada;
    private boolean btnCirculoPulsado;
    private boolean btnTrianguloPulsado;
    private boolean hiloIniciado = false;

    /**
     * Comprueba si hay input conectado
     *
     * @return true si lo hay false si no lo hay
     */
    private boolean hayInput() {
        // Analizamos los dispositivos conectados y los guardamos en una lista
        net.java.games.input.Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        boolean inputEncontrado = false;
        // Recorremos esta lista
        for (net.java.games.input.Controller controller : controllers) {
            // Si encontramos el mando de PS4 inicializamos el controller
            if (controller.getName().replace(" ", "").equalsIgnoreCase("wirelessController")) {
                ps4Input = controller;
                inputEncontrado = true;
                break;
            }
        }
        return inputEncontrado;
    }

    /**
     * Hilo que mueve el puntero y cambia los datos de pulsación de los botones
     */
    @Override
    public void run() {
        hiloIniciado = true;
        while (hayInput()) {
            ps4Input.poll();
            Event event = new Event();
            while (ps4Input.getEventQueue().getNextEvent(event)) {
                Component component = event.getComponent();
                if (component.getName().equals("Botón 5") && event.getValue() == 1.0f) {
                    if (++posicionPuntero > limitePuntero) {
                        posicionPuntero = 1;
                    }
                } else if (component.getName().equals("Botón 4") && event.getValue() == 1.0f) {
                    if (--posicionPuntero < 1) {
                        posicionPuntero = limitePuntero;
                    }
                } else if (component.getName().equals("Botón 1") && event.getValue() == 1.0f) {
                    btnEquisPulsada = true;
                } else if (component.getName().equals("Botón 2") && event.getValue() == 1.0f) {
                    btnCirculoPulsado = true;
                } else if (component.getName().equals("Botón 3") && event.getValue() == 1.0f) {
                    btnTrianguloPulsado = true;
                }
            }
        }
    }
}
