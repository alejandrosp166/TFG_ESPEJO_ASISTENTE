package es.tfg.asp.modelo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CredencialesUsuario {
    @Id
    private int idCredenciales;
    @Column(name = "password")
    private String password;
    @Column(name = "id_usuario")
    private int idUsuario;
}
