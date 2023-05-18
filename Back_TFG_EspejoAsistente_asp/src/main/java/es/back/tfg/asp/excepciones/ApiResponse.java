package es.back.tfg.asp.excepciones;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private int codigoEstado;
    private String mensaje;
}
