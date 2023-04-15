package es.tfg.asp.controlador;

import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import org.springframework.stereotype.Controller;

/**
 * Esta clase permite moverse al usuario por la interfaz
 */
@Controller
public class MandoControllerGeneral implements Runnable {
    net.java.games.input.Controller ps4Input;
    int posicionPuntero;
    private net.java.games.input.Controller[] controllers;
    private boolean inputEncontrado;

    public MandoControllerGeneral() {
        // Analizamos los dispositivos conectados y los guardamos en una lista
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        ps4Input = null;
        posicionPuntero = 1;
        // Recorremos esta lista
        for (net.java.games.input.Controller controller : controllers) {
            // Si encontramos el mando de PS4 inicializamos el controller
            if (controller.getName().replace(" ", "").equalsIgnoreCase("wirelessController")) {
                ps4Input = controller;
                inputEncontrado = true;
                break;
            }
        }
    }

    // Start Botón 9 // r1 Botón 5 // l1 botón 4 // panel Botón 13 // share Botón 8 // ps Botón 12
    @Override
    public void run() {
        while (true) {
            if (inputEncontrado) {
                ps4Input.poll();
                EventQueue queue = ps4Input.getEventQueue();
                Event event = new Event();
                while (queue.getNextEvent(event)) {
                    Component component = event.getComponent();
                    if (component.getName().equals("Botón 5") && event.getValue() == 1.0f) {
                        posicionPuntero++;
                        System.out.println(posicionPuntero);
                    } else if (component.getName().equals("Botón 4") && event.getValue() == 1.0f) {
                        posicionPuntero--;
                        System.out.println(posicionPuntero);
                    }
                }
            } else {
                System.out.println("No hay input");
            }
        }
    }
}
