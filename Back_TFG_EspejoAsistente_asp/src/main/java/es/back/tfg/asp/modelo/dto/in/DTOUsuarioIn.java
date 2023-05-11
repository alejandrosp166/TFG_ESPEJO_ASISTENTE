package es.back.tfg.asp.modelo.dto.in;

import lombok.Data;

@Data
public class DTOUsuarioIn {
    private int idUsuario;
    private String uuid;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean esAdmin;
    private DTOCredencialesIn dtoCredencialesIn;
}