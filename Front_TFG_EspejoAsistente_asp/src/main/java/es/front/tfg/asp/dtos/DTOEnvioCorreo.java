package es.front.tfg.asp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOEnvioCorreo {
    private String emailPara;
    private String username;
}
