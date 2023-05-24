package es.front.tfg.asp.modelo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private int codigoEstado;
    private String mensaje;
}
