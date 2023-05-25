package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.dtos.DTOEquipo;

import java.io.IOException;
import java.util.List;

public interface IServiceEquipo {
    public List<DTOEquipo> obtenerEquiposLigaSantander();
}
