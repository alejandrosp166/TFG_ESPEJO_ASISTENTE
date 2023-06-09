package es.back.tfg.asp.excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExcepcionBuscarEntidad extends RuntimeException {
    private String mensaje;

    public ExcepcionBuscarEntidad(String mensaje) {
        this.mensaje = mensaje;
    }
}
