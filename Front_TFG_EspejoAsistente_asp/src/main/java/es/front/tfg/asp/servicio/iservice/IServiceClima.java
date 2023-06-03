package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.response.ResponseClima;

import java.util.List;

public interface IServiceClima {
    public ResponseClima obtenerDatosClimaticosActualesPorCodigoPostal(String codigoPostal);

    public List<ResponseClima> obtenerDatosClimaticosProximosDiasPorCodigoPostal(String codigoPostal);
}
