package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "clima_localizacion_usuario")
public class LocalizacionClima extends UuId {
    private String nombre;
    private String pais;
    private String codigoPostal;
    @OneToMany(mappedBy = "localizacionClima")
    private List<Usuario> usuarios;
}
