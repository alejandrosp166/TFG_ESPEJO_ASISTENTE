package es.front.tfg.asp.modelo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOEquipo {
    private Team team;
    private Venue venue;

    @Data
    @AllArgsConstructor
    public static class Team {
        private String id;
        private String name;
        private String code;
        private int founded;
        private String logo;
    }

    @Data
    @AllArgsConstructor
    public static class Venue {
        private String id;
        private String name;
        private String city;
        private String capacity;
        private String image;
    }
}
