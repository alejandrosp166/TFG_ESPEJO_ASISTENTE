package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.util.TypeUtils;

import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "credenciales_usuario")
public class CredencialesUsuario extends UuId{
    @Column(name = "password")
    private String password;
    @OneToOne
    @JoinColumn(name = "uuid_usuario")
    private Usuario uuIdUsuario;
}
