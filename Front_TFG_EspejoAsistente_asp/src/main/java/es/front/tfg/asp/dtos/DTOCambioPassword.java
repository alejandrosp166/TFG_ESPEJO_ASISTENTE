package es.front.tfg.asp.dtos;

import lombok.Data;

@Data
public class DTOCambioPassword {
    private String tokenSeguridad;
    private String nuevaPassword;
}
