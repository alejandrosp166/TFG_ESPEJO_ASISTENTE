package es.back.tfg.asp.excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExcepcionCambiarContrasenna extends RuntimeException {
    private String mensaje;

    public ExcepcionCambiarContrasenna(String mensaje) {
        this.mensaje = mensaje;
    }
}
