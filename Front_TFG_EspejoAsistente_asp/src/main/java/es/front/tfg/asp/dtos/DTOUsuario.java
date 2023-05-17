package es.front.tfg.asp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOUsuario {
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean admin;
    private String password;
    private String tokenSeguridad;
    private String codigoVerificacionCambioContrasenna;
}
