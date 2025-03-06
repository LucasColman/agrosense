package com.unam.agrosense.controllers;


import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.sensor.SensorDto;
import com.unam.agrosense.model.sensor.SensorResponseDto;
import com.unam.agrosense.services.SensorService;
import com.unam.agrosense.services.TipoSensorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/sensores")
public class SensorController {

    private final SensorService sensorService;
    private final TipoSensorService tipoSensorService;

    public SensorController(SensorService sensorService, TipoSensorService tipoSensorService) {
        this.sensorService = sensorService;
        this.tipoSensorService = tipoSensorService;
    }

    //Registrar UN SENSOR
    @PostMapping("/store")
    public ResponseEntity<SensorResponseDto> registrarSensor(@ModelAttribute @Valid SensorDto sensorDto, UriComponentsBuilder uriBuilder) {
        SensorResponseDto sensorResponseDto = sensorService.crearSensor(sensorDto);

        URI url = uriBuilder.path("/sensores/{id}").buildAndExpand(sensorResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(sensorResponseDto);
    }

    // ACTUALIZAR UN SENSOR
    @PutMapping("/edit/{id}")
    public ResponseEntity<SensorResponseDto> actualizarSensor(@ModelAttribute @RequestBody @Valid SensorDto sensorDto, @PathVariable Long id) {
        SensorResponseDto sensorResponseDto = sensorService.actualizarSensor(id, sensorDto);
        return ResponseEntity.ok(sensorResponseDto);
    }

    // ELIMINAR UN SENSOR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarSensor(@PathVariable Long id) {
        sensorService.eliminarSensor(id);
        return ResponseEntity.noContent().build();
    }

    // OBTENER UN SENSOR
    @GetMapping("/{id}")
    public ResponseEntity<SensorResponseDto> obtenerSensor(@PathVariable Long id) {
        SensorResponseDto sensorResponseDto = sensorService.obtenerSensor(id);
        return ResponseEntity.ok(sensorResponseDto);
    }

//    @GetMapping
//    public ResponseEntity<List<SensorResponseDto>> listarSensores() {
//        List<SensorResponseDto> sensores =  sensorService.obtenerSensores();
//        return ResponseEntity.ok().body(sensores);
//   }

    @GetMapping("/cantidad")
    public ResponseEntity<Integer> cantidadDeSensores() {
        return ResponseEntity.ok().body(sensorService.cantidadDeSensores());
    }

    @GetMapping
    public String listarSensores(Model model) {
        List<SensorResponseDto> sensores = sensorService.obtenerSensores();
        model.addAttribute("sensores", sensores);
        model.addAttribute("tiposDispositivo", TipoDispositivo.values());
        model.addAttribute("tiposSensores", tipoSensorService.obtenerTiposSensores());
        return "/dispositivos/Sensores";
    }
}
