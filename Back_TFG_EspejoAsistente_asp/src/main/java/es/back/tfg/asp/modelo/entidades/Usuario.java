package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "usuario")
public class Usuario extends UuId {
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private String tokenSeguridad;
    private String codigoVerificacionCambioContrasenna;
    @Column(name = "es_admin")
    private boolean admin;
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private CredencialesUsuario credencialesUsuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid_equipo")
    private Equipo equipo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uuid_localizacion")
    private LocalizacionClima localizacionClima;
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
