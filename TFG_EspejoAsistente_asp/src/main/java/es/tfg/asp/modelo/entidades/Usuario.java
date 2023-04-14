package es.tfg.asp.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    private int idUsuario;
}
