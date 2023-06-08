package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "equipo_usuario")
public class Equipo extends UuId{
    private String nombre;
    private String liga;
    @OneToMany(mappedBy = "equipo")
    private List<Usuario> usuarios;
}
