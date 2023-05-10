package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "credenciales")
public class CredencialesUsuario {
    @Id
    @Column(name = "id_credenciales")
    private int idCredenciales;
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;
}
