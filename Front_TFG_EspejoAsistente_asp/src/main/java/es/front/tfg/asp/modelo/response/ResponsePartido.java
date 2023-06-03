package es.front.tfg.asp.modelo.response;

import lombok.Data;

@Data
public class ResponsePartido {
    private int id;
    private String local;
    private String visitor;
    private String logo;
    private String local_shield;
    private String visitor_shield;
    private String minute;
    private String live_minute;
    private String result;
}
