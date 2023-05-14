package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "equipo_usuario")
public class Equipo {
    @Id
    private String uuIdEquipo;
    private String nombre;
}
