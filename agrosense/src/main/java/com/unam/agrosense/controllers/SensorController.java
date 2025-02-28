package com.unam.agrosense.controllers;


import com.unam.agrosense.model.sensor.SensorDto;
import com.unam.agrosense.model.sensor.SensorResponseDto;
import com.unam.agrosense.services.SensorService;
import com.unam.agrosense.services.TipoSensorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sensores")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService, TipoSensorService tipoSensorService) {
        this.sensorService = sensorService;
    }


    //Registrar UN SENSOR
    @PostMapping
    public ResponseEntity<SensorResponseDto> registrarSensor(@RequestBody @Valid SensorDto sensorDto, UriComponentsBuilder uriBuilder) {
        SensorResponseDto sensorResponseDto = sensorService.crearSensor(sensorDto);

        URI url = uriBuilder.path("/sensores/{id}").buildAndExpand(sensorResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(sensorResponseDto);
    }

    // ACTUALIZAR UN SENSOR
    @PutMapping("/{id}")
    public ResponseEntity<SensorResponseDto> actualizarSensor(@RequestBody @Valid SensorDto sensorDto, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
        SensorResponseDto sensorResponseDto = sensorService.actualizarSensor(id, sensorDto);
        return ResponseEntity.ok(sensorResponseDto);
    }

    // ELIMINAR UN SENSOR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSensor(@PathVariable Long id) {
        sensorService.eliminarSensor(id);
        return ResponseEntity.noContent().build();
    }
    // OBTENER UN SENSOR
    @GetMapping("/{id}")
    public ResponseEntity<SensorResponseDto> obtenerSensor(@PathVariable Long id) {
        SensorResponseDto sensorResponseDto = sensorService.obtenerSensor(id);
        return ResponseEntity.notFound().build();
    }

    // OBTENER TODOS LOS SENSORES
    @GetMapping
    public ResponseEntity<List<SensorResponseDto>> obtenerSensores() {
        List<SensorResponseDto> sensorResponseDto = sensorService.obtenerSensores();

        return ResponseEntity.ok(sensorResponseDto);
    }
}
