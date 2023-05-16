package es.front.tfg.asp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOCambioPassword {
    private String tokenSeguridad;
    private String nuevaPassword;
}
