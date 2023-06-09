package es.front.tfg.asp.utils;

import es.front.tfg.asp.modelo.dtos.DTOUsuarioOut;
import es.front.tfg.asp.servicio.iservice.IServiceUsuario;
import javafx.event.ActionEvent;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

@Component
@Setter
public class Datos {
    @Autowired
    private IServiceUsuario serviceUsuario;
    @Autowired
    private Utiles utiles;
    private DTOUsuarioOut usuarioLogeado;

    /**
     * Cierra sesión en el sistema
     *
     * @param e        evento del elemento
     * @param c        clase desde la cual se llama
     * @param resource la vista a la que se va a mover
     */
    public void cerrarSesion(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
        borrarPropiedades();
        usuarioLogeado = null;
    }

    /**
     * Devuelve el usuario logeado, en caso de estar ya logado no se hace de nuevo la petición
     *
     * @return el usuario logado
     */
    public DTOUsuarioOut obtenerUsuarioLogeado() {
        if (Objects.isNull(usuarioLogeado)) {
            Object respuesta = serviceUsuario.obtenerUsuarioPorUuid(obtenerElementoPropieades("uuidUsuario"));
            if (respuesta instanceof DTOUsuarioOut usuario) {
                usuarioLogeado = usuario;
                guardarElementoPropiedades("uuidUsuario", usuarioLogeado.getUuid());
            }
        }
        return usuarioLogeado;
    }

    /**
     * Guarda elementos en el archivo propiedades
     *
     * @param key       la key del elemento
     * @param contenido el contenido
     */
    public void guardarElementoPropiedades(String key, String contenido) {
        Properties properties = new Properties();
        properties.setProperty(key, contenido);
        try (OutputStream escribir = new FileOutputStream("session-config.properties")) {
            properties.store(escribir, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene datos del archivo propiedades
     *
     * @param key la key del elemento
     * @return el contido guardado
     */
    public String obtenerElementoPropieades(String key) {
        String propiedad = null;
        try (InputStream leer = new FileInputStream("session-config.properties")) {
            Properties properties = new Properties();
            properties.load(leer);
            propiedad = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propiedad;
    }

    /**
     * Borra las propiedades
     */
    public void borrarPropiedades() {
        File propiedades = new File("session-config.properties");
        if (propiedades.exists()) {
            boolean eliminado = propiedades.delete();
            if (!eliminado) {
                utiles.crearModal("Error", "Hubo un error en el cliente al cerrar la sesión");
            }
        }
    }
}
