package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "equipo_usuario")
public class Equipo extends UuId{
    private String nombre;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    @OneToOne
    @JoinColumn(name = "uuid_usuario")
    private Usuario usuario;
}
