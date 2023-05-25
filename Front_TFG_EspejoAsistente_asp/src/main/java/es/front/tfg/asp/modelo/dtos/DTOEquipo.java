package es.front.tfg.asp.modelo.dtos;

import lombok.Data;

@Data
public class DTOEquipo {
    private Team team;
    private Venue venue;

    @Data
    public static class Team {
        private String id;
        private String name;
        private String code;
        private int founded;
        private String logo;
    }

    @Data
    public static class Venue {
        private String id;
        private String name;
        private String city;
        private String capacity;
        private String image;
    }
}
