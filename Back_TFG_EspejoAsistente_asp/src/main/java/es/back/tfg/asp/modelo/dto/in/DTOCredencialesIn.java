package es.back.tfg.asp.modelo.dto.in;

import lombok.Data;

@Data
public class DTOCredencialesIn {
    private int idCredenciales;
    private String password;
    private int idUsuario;
}
