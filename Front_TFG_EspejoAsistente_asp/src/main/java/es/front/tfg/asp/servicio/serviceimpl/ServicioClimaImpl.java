package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.servicio.iservice.IServiceClima;
import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioClimaImpl implements IServiceClima {
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?zip=41020,es&appid=8d0067ebec1efdddbe5a7db4bd07a7f8&units=metric&lang=es";
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    @Override
    public ResponseClima obtenerDatosClimaticosActualesPorCodigoPostal(String codigoPostal) {
        return peticionesHTTP.get(URL, ResponseClima.class);
    }

    @Override
    public List<ResponseClima> obtenerDatosClimaticosProximosDiasPorCodigoPostal(String codigoPostal) {
        return peticionesHTTP.getListas(URL.replace("weather?", "forecast?"), ResponseClima.class, "list");
    }


}
