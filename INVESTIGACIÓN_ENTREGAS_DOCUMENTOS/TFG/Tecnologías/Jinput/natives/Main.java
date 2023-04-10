import net.java.games.input.*;

public class Main {
    public static void main(String[] args) {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Controller ps4 = null;
        for (Controller controller : controllers) {
            if (controller.getName().contains("Wireless Controller")) {
                ps4 = controller;
                break;
            }
        }
        if (ps4 == null) {
            System.out.println("NO SE ENCONTRÓ EL INPUT DEL MANDO");
        } else {
            while (true) {
                ps4.poll();
                EventQueue queue = ps4.getEventQueue();
                Event event = new Event();
                while (queue.getNextEvent(event)) {
                    Component component = event.getComponent();
                    if (component.getName().equals("Botón 2") && event.getValue() == 1.0f) {
                        System.out.println("Botón Pulsado!");
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}