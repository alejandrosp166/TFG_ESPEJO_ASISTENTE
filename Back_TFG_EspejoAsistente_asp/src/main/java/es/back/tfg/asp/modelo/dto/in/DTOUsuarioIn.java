package es.back.tfg.asp.modelo.dto.in;

import lombok.Data;

@Data
public class DTOUsuarioIn {
    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean admin;
    private String password;
    private DTOEquipoIn equipo;
    private DTOLocalizacionClimaIn localizacionClima;

    @Data
    public static class DTOEquipoIn {
        private String liga;
        private String nombreEquipo;
    }

    @Data
    public static class DTOLocalizacionClimaIn {
        private String pais;
        private String codigoPostal;
    }
}
