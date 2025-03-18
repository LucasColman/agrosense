package com.unam.agrosense.services;

import com.unam.agrosense.model.datoSensor.DatoSensorDto;
import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class SimuladorSensorService {

    @Autowired
    private SensorRepository sensorRepository; // Para obtener sensores dinámicamente

    @Autowired
    private DatoSensorService datoSensorService; // Servicio para guardar mediciones
    private final Random random = new Random();

    @Transactional
    @Scheduled(fixedRate = 120000) // Cada 2 minutos
    public void simularMediciones() {
        System.out.println("Ejecutando simulación de mediciones...");

        List<Sensor> sensores = sensorRepository.findAllByActivoTrue();

        if(!sensores.isEmpty()) {
            sensores.forEach(sensor -> {
                sensor.getTiposSensores().forEach(tipoSensor -> {
                    String tipoMedida = tipoSensor.getTipoMedida().name();

                    BigDecimal valor = generarValorAleatorio(tipoMedida);

                    DatoSensorDto datoSensorDto = new DatoSensorDto(
                            valor,
                            sensor
                    );

                    datoSensorService.crearDatoSensor(datoSensorDto);
                });
            });
        }else{
            System.out.println("No hay sensores activos");
        }

    }

    // Genera un valor aleatorio según la unidad
    private BigDecimal generarValorAleatorio(String unidad) {
        if ("HUMEDAD".equals(unidad)) {
            // Temperatura entre 10 y 30 °C
            return BigDecimal.valueOf(10 + random.nextDouble() * 20);
        } else if ("TEMPERATURA".equals(unidad)) {
            // Humedad entre 0 y 100%
            return BigDecimal.valueOf(random.nextDouble() * 100);
        } else {
            // Rango genérico
            return BigDecimal.valueOf(random.nextDouble() * 100);
        }
    }
}
