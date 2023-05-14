package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Usuario extends UuId {
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    @Column(name = "es_admin")
    private boolean admin;
    @OneToOne(mappedBy = "usuario")
    private CredencialesUsuario credencialesUsuario;

    @Override
    public String toString() {
        return "Usuario{" +
                "uuid='" + super.uuid + '\'' +
                ", username='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                '}';
    }
}
