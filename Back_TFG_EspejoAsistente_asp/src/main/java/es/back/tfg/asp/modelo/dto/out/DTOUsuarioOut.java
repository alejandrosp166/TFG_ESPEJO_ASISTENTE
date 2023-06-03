package es.back.tfg.asp.modelo.dto.out;

import lombok.Data;

import java.util.UUID;

@Data
public class DTOUsuarioOut {
    private UUID uuid;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean esAdmin;
    private String tokenSeguridad;
    private String codigoVerificacionCambioContrasenna;
    private UUID uuidCredenciales;
    private String equipoFav;
    private UUID uuidEquipo;
    private String localizacion;
    private UUID uuidLocalizacion;
}
