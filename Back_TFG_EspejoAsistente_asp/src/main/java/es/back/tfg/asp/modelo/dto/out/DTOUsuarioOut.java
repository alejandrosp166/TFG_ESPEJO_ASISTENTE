package es.back.tfg.asp.modelo.dto.out;

import lombok.Data;

@Data
public class DTOUsuarioOut {
    private String uuid;
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean esAdmin;
    private String uuidCredenciales;
}
