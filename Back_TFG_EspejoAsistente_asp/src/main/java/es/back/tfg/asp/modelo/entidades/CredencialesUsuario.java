package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "credenciales_usuario")
public class CredencialesUsuario extends UuId{
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "uuid_usuario")
    private Usuario usuario;

    public CredencialesUsuario(String password, Usuario usuario) {
        this.password = password;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "CredencialesUsuario{" +
                "uuid='" + super.uuid + '\'' +
                ", password='" + password + '\'' +
                ", uuIdUsuario='" + usuario.getUuid() + '\'' +
                '}';
    }
}
