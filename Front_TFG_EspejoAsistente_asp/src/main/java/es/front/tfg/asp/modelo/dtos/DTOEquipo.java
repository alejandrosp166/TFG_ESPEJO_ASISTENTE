package es.front.tfg.asp.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOEquipo {
    int idEquipoApi;
    private String liga;
    private String nombreEquipo;
}
