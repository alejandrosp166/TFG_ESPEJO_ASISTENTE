package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponseLiga;
import es.front.tfg.asp.modelo.response.ResponsePartido;
import es.front.tfg.asp.servicio.iservice.IServiceEquipo;

import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ServicioEquipoImpl implements IServiceEquipo {
    private final String URL = "https://v3.football.api-sports.io/teams?country=PAIS";
    // https://v3.football.api-sports.io/leagues?country=england
    // https://v3.football.api-sports.io/teams?league=39&season=2019
    // https://v3.football.api-sports.io/standings?league=39&season=2022 // TABLA CLASIFICACIÓN
    // https://v3.football.api-sports.io/odds/live?league=39 partidos en vivo
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    /**
     * Obtiene un equipo por su id
     *
     * @param id el id del equipo
     * @return una lista de ResponseEquipo
     */
    @Override
    public List<ResponseEquipo> obtenerEquipoPorId(String id) {
        return peticionesHTTP.getListas(URL.replace("country=PAIS", "id=" + id), ResponseEquipo.class, "response");
    }

    /**
     * Obtiene una lista de equipos por el país
     *
     * @param pais el país
     * @return una lista de equipos
     */
    @Override
    public List<ResponseEquipo> obtenerEquiposPorPais(String pais) {
        return peticionesHTTP.getListas(URL.replace("PAIS", pais), ResponseEquipo.class, "response");
    }

    /**
     * Obtiene una clasificación de una liga
     *
     * @param liga la liga a obtener
     * @return la clasificación
     */
    @Override
    public List<ResponseLiga> obtenerClasificacionLiga(String liga) {
        String url = URL.replace("teams?country=PAIS", "standings?league=" + liga + "&season=2022");
        return peticionesHTTP.getListas(url, ResponseLiga.class, "response");
    }

    /**
     * Obtiene partidos en vivo de una liga
     *
     * @param liga la liga
     * @return la lista de partidos en vivo
     */
    @Override
    public List<ResponsePartido> obtenerPartidosEnVivoLiga(String liga) {
        String url = URL.replace("teams?country=PAIS", "odds/live?league=" + liga);
        return peticionesHTTP.getListas(url, ResponsePartido.class, "response");
    }
}
