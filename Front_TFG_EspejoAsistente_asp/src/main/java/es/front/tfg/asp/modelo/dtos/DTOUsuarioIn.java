package es.front.tfg.asp.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOUsuarioIn {
    private String uuid;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean admin;
    private String password;
    private DTOEquipo equipo;
    private DTOLocalizacionClima localizacionClima;
}
