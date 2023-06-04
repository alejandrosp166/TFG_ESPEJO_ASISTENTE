package es.front.tfg.asp.servicio.iservice;

import es.front.tfg.asp.modelo.response.ResponseClima;

import java.util.List;

public interface IServiceClima {
    public ResponseClima obtenerDatosClimaticosActualesPorCodigoPostalPais(String codigoPostal, String pais);

    public List<ResponseClima> obtenerDatosClimaticosProximosDiasPorCodigoPostalPais(String codigoPostal, String pais);
}
