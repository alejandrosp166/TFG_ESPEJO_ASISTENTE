package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "usuario")
public class Usuario extends UuId {
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    @Column(name = "es_admin")
    private boolean admin;
    @OneToOne(mappedBy = "uuIdUsuario")
    private CredencialesUsuario credencialesUsuario;
}
