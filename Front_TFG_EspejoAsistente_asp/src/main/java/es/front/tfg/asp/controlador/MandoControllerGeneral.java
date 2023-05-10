package es.front.tfg.asp.controlador;

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
    private net.java.games.input.Controller ps4Input;
    private int posicionPuntero;
    private boolean confirmarPulsado;
    public MandoControllerGeneral() {
        posicionPuntero = 1;
    }

    private boolean hayInput() {
        // Analizamos los dispositivos conectados y los guardamos en una lista
        net.java.games.input.Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        ps4Input = null;
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

    // Start Botón 9 // r1 Botón 5 // l1 botón 4 // panel Botón 13 // share Botón 8 // ps Botón 12
    @Override
    public void run() {
        while (hayInput()) {
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
                } else if(component.getName().equals("Botón 1") && event.getValue() == 1.0f) {
                    confirmarPulsado = true;
                }
            }
        }
        System.out.println("No hay input!");
        posicionPuntero = 1;
    }

    public boolean isConfirmarPulsado() {
        return confirmarPulsado;
    }

    public void setConfirmarPulsado(boolean confirmarPulsado) {
        this.confirmarPulsado = confirmarPulsado;
    }

    public int getPosicionPuntero() {
        return posicionPuntero;
    }

    public void setPosicionPuntero(int posicionPuntero) {
        this.posicionPuntero = posicionPuntero;
    }
}
