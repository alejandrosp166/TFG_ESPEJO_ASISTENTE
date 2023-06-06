package es.front.tfg.asp.modelo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class DTOUsuarioOut {
    private String uuid;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean esAdmin;
    private String tokenSeguridad;
    private String codigoVerificacionCambioContrasenna;
    private String uuidCredenciales;
    private String equipoFav;
    private String paisLiga;
    private UUID uuidEquipo;
    private String paisClima;
    private String codigoPostal;
    private String uuidLocalizacion;
}
