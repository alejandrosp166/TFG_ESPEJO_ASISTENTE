package es.back.tfg.asp.modelo.dto.in;

import lombok.Data;

@Data
public class DTOCambioPasswordIn {
    private String tokenSeguridad;
    private String nuevaPassword;
}
