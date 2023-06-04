package es.front.tfg.asp.modelo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseClima {
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private String dt_txt;

    @Data
    @AllArgsConstructor
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @AllArgsConstructor
    public static class Main {
        private String temp;
        private String feels_like;
        private String temp_min;
        private String temp_max;
        private String pressure;
        private String humidity;
    }
    @Data
    @AllArgsConstructor
    public static class Wind {
        private String speed;
        private String deg;
    }
}
