package es.front.tfg.asp.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOUsuario {
    private String uuid;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean esAdmin;
    private String password;
    private String equipoFav;
    private String tokenSeguridad;
    private String codigoVerificacionCambioContrasenna;
}
