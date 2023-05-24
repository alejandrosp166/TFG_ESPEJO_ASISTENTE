package es.front.tfg.asp.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOEnvioCorreo {
    private String emailPara;
    private String username;
}
