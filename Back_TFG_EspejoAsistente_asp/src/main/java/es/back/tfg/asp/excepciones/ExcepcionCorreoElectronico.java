package es.back.tfg.asp.excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExcepcionCorreoElectronico extends RuntimeException {
    private String mensaje;

    public ExcepcionCorreoElectronico(String mensaje) {
        this.mensaje = mensaje;
    }
}
