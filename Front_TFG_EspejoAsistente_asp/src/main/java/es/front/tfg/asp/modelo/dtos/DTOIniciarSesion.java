package es.front.tfg.asp.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOIniciarSesion {
    private String username;
    private String password;
}
