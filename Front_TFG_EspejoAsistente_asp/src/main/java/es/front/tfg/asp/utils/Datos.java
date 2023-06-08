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

    public void cerrarSesion(ActionEvent e, Class<?> c, String resource) {
        utiles.cambiarVentanaAplicacion(e, c, resource);
        // borrarPropiedades();
        usuarioLogeado = null;
    }

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

    public void guardarElementoPropiedades(String key, String contenido) {
        Properties properties = new Properties();
        properties.setProperty(key, contenido);
        try (OutputStream escribir = new FileOutputStream("session-config.properties")) {
            properties.store(escribir, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void borrarPropiedades() {
        try (InputStream leer = new FileInputStream("session-config.properties")) {
            Properties properties = new Properties();
            properties.load(leer);
            properties.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
