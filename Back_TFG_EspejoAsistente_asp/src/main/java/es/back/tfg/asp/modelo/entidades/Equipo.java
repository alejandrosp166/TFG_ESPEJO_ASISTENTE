package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Data
@Table(name = "equipo_usuario")
public class Equipo {
    @Id
    private UUID uuIdEquipo;
    private String nombre;
}
