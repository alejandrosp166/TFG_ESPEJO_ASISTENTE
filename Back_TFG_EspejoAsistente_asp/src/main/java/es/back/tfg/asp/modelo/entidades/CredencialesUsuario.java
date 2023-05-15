package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "credenciales_usuario")
public class CredencialesUsuario extends UuId{
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "uuid_usuario")
    private Usuario usuario;

    @Override
    public String toString() {
        return "CredencialesUsuario{" +
                "uuid='" + super.uuid + '\'' +
                ", password='" + password + '\'' +
                ", uuIdUsuario='" + usuario.getUuid() + '\'' +
                '}';
    }
}
