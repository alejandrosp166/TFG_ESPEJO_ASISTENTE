package es.back.tfg.asp.modelo.dto.in;

import lombok.Data;

@Data
public class DTOUsuarioInActualizar {
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean admin;
    private String password;
    private DTOUsuarioIn.DTOEquipoIn equipo;
    private DTOUsuarioIn.DTOLocalizacionClimaIn localizacionClima;
}
