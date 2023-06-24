package microservicios.planillaGenerate.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservicios.planillaGenerate.Models.AcopioM;
import microservicios.planillaGenerate.Repositories.PlanillaR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PagoS {
    @Autowired
    PlanillaR planillaR;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RestTemplate restTemplate;

    public List<AcopioM> obtenerAcopioActual(String codiigo){
        List<AcopioM> acopio = restTemplate.getForObject("http://acopioImport/acopio/actual/"+ codiigo, List.class);
        System.out.println(acopio);
        return acopio;
    }
    public List<AcopioM> obtenerAcopioAnterior(String codiigo){
        List<AcopioM> acopio = restTemplate.getForObject("http://acopioImport/acopio/anterior/"+ codiigo, List.class);
        System.out.println(acopio);
        return acopio;
    }

    public double pagoFinal(double pago){
        if(pago > 950000){
            return pago - (pago * 0.13);
        }
        return pago;
    }
}
