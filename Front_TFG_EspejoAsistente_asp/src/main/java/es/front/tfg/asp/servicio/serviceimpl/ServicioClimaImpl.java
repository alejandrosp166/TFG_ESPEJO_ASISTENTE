package es.front.tfg.asp.servicio.serviceimpl;

import es.front.tfg.asp.modelo.response.ResponseClima;
import es.front.tfg.asp.servicio.iservice.IServiceClima;
import es.front.tfg.asp.utils.PeticionesHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioClimaImpl implements IServiceClima {
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?zip=COD_POSTAL,PAIS&appid=8d0067ebec1efdddbe5a7db4bd07a7f8&units=metric&lang=es";
    @Autowired
    private PeticionesHTTP peticionesHTTP;

    @Override
    public ResponseClima obtenerDatosClimaticosActualesPorCodigoPostalPais(String codigoPostal, String pais) {
        String url = URL.replace("COD_POSTAL", codigoPostal).replace("PAIS", pais);
        return peticionesHTTP.get(url, ResponseClima.class);
    }

    @Override
    public List<ResponseClima> obtenerDatosClimaticosProximosDiasPorCodigoPostalPais(String codigoPostal, String pais) {
        String url = URL.replace("weather?", "forecast?").replace("COD_POSTAL", codigoPostal).replace("PAIS", pais);
        return peticionesHTTP.getListas(url, ResponseClima.class, "list");
    }


}
