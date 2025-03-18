package com.unam.agrosense.controllers;

import com.unam.agrosense.model.datoSensor.DatoSensorDto;
import com.unam.agrosense.model.datoSensor.DatoSensorResponseDto;
import com.unam.agrosense.model.datoSensor.MedicionDto;
import com.unam.agrosense.services.DatoSensorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/dato-sensor")
public class DatoSensorController {
    private final DatoSensorService datoSensorService;

    public DatoSensorController(DatoSensorService datoSensorService) {
        this.datoSensorService = datoSensorService;
    }


    // Registrar un nuevo dato de sensor
    @PostMapping("/store")
    public ResponseEntity<DatoSensorResponseDto> registrarDatoSensor(@ModelAttribute @RequestBody @Valid DatoSensorDto datoSensorDto, UriComponentsBuilder uriBuilder) {
        DatoSensorResponseDto datoSensorResponseDto = datoSensorService.crearDatoSensor(datoSensorDto);

        URI url = uriBuilder.path("/dato-sensor/{id}").buildAndExpand(datoSensorResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(datoSensorResponseDto);

    }
    // Actualizar un dato de sensor
    @PutMapping("/edit/{id}")
    public ResponseEntity<DatoSensorResponseDto> actualizarDatoSensor(@ModelAttribute @PathVariable Long id,  @RequestBody @Valid DatoSensorDto datoSensorDto, UriComponentsBuilder uriBuilder) {
        DatoSensorResponseDto datoSensorResponseDto = datoSensorService.crearDatoSensor(datoSensorDto);
        return ResponseEntity.ok(datoSensorResponseDto);
    }
    // Eliminar un dato de sensor
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarDatoSensor(@PathVariable Long id) {
        datoSensorService.eliminarDatoSensor(id);
        return ResponseEntity.noContent().build();
    }
    // Obtener un dato de sensor
    @GetMapping("/{id}")
    public ResponseEntity<DatoSensorResponseDto> obtenerDatoSensor(@PathVariable Long id) {
        DatoSensorResponseDto datoSensorResponseDto = datoSensorService.obtenerDatoSensor(id);
        return ResponseEntity.ok(datoSensorResponseDto);
    }
    // Obtener todos los datos de sensor
    @GetMapping
    public String listarDatosSensor(Model model) {
        List<DatoSensorResponseDto> datosSensor = datoSensorService.obtenerDatosSensor();

        model.addAttribute("datos-sensor", datosSensor);
        model.addAttribute("sensores", datoSensorService.obtenerSensores());
        return "datosSensor";
    }


//    @GetMapping("/historial")
//    public ResponseEntity<List<MedicionDto>> obtenerHistorialMediciones(){
//        List<MedicionDto> historial = datoSensorService.obtenerHistorialMediciones();
//        return ResponseEntity.ok(historial);
//    }


    @GetMapping("/historial")
    public String obtenerHistorialMediciones(Model model) {
        List<MedicionDto> historial = datoSensorService.obtenerHistorialMediciones();
        model.addAttribute("historial", historial);
        return "historial_mediciones";
    }


}
