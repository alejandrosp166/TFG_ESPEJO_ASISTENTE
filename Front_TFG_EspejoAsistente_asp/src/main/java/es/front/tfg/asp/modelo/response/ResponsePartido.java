package es.front.tfg.asp.modelo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePartido {
    private Estado fixture;
    public Equipos teams;
    @Data
    @AllArgsConstructor
    public static class Estado {
        private int id;
        private Status status;
    }
    @Data
    @AllArgsConstructor
    public static class Equipos {
        private Home home;
        private Away away;
    }
    @Data
    @AllArgsConstructor
    public static class Home {
        public int id;
        public int goals;
    }
    @Data
    @AllArgsConstructor
    public static class Away {
        private int id;
        private int goals;
    }
    @Data
    @AllArgsConstructor
    public static class Status {
        private String mylong;
        private int elapsed;
        private String seconds;
    }
}
