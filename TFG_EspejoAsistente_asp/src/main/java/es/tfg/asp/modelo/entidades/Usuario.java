package es.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "id_usuario")
    private int idUsuario;
    @Column(name = "username")
    private String username;
    @OneToOne(mappedBy = "idUsuario")
    private CredencialesUsuario credenciales;
}
