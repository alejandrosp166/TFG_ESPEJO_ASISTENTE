package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.response.ResponseEquipo;
import es.front.tfg.asp.modelo.response.ResponseLiga;
import es.front.tfg.asp.modelo.response.ResponsePartido;

import java.util.List;

public interface IServiceEquipo {
    public List<ResponseEquipo> obtenerEquiposPorPais(String pais);
    public List<ResponseLiga> obtenerPartidosLiga();
}
