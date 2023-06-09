package es.back.tfg.asp.excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExcepcionInicioSesion extends RuntimeException {
    private String mensaje;

    public ExcepcionInicioSesion(String mensaje) {
        this.mensaje = mensaje;
    }
}
