package es.front.tfg.asp.modelo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseLiga {
    private Liga league;
    @Data
    @AllArgsConstructor
    public static class Liga {
        private String name;
        private String logo;
        List<List<Clasificacion>> standings;
    }
    @Data
    @AllArgsConstructor
    public static class Clasificacion {
        private int rank;
        private Equipo team;
        private int points;
        private int goalsdiff;
        private Detalles all;
    }
    @Data
    @AllArgsConstructor
    public static class Equipo {
        private String name;
        private String logo;
    }
    @Data
    @AllArgsConstructor
    public static class Detalles {
        private int palyed;
        private int win;
        private int draw;
        private int lose;
    }
}
