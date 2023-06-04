package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponsePartido;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;

import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEquipoImpl implements IServiceEquipo {
    private final String URL = "https://v3.football.api-sports.io/teams?country=PAIS";
    // https://v3.football.api-sports.io/leagues?country=england
    // https://v3.football.api-sports.io/teams?league=39&season=2019
    // https://v3.football.api-sports.io/standings?league=39&season=2022 // TABLA CLASIFICACIÓN
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    @Override
    public List<ResponseEquipo> obtenerEquiposPorPais(String pais) {
        return peticionesHTTP.getListas(URL.replace("PAIS", pais), ResponseEquipo.class, "response");
    }

    public List<ResponsePartido> obtenerPartidosHoy() {
        return peticionesHTTP.getListas(URL + "&req=matchsday", ResponsePartido.class, "response");
    }
}
