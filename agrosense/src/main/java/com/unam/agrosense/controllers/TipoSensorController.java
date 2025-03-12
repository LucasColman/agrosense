package com.unam.agrosense.controllers;

import com.unam.agrosense.model.sensor.SensorDto;
import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.model.tipoSensor.TipoSensorResponseDto;
import com.unam.agrosense.services.TipoSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tipos-sensores")
public class TipoSensorController {
    @Autowired
    private TipoSensorService tipoSensorService;

    @PostMapping("/store")
    public ResponseEntity<TipoSensor> registrarTipoSensor(@ModelAttribute TipoSensor tipoSensor) {
        return ResponseEntity.ok(tipoSensorService.registrarTipoSensor(tipoSensor));
    }

    @GetMapping
    public String listarTiposSensores(Model model) {
        List<TipoSensorResponseDto> tiposSensores = tipoSensorService.obtenerTiposSensores();
        model.addAttribute("tiposSensores", tiposSensores);
        model.addAttribute("tiposMedidas", TipoMedida.values());
        return "dispositivos/TipoSensores";
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSensor> obtenerTipoSensor(@PathVariable Long id) {
        return tipoSensorService.obtenerTipoSensor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TipoSensor> actualizarTipoSensor(@ModelAttribute @RequestBody TipoSensor tipoSensor, @PathVariable Long id) {
        return ResponseEntity.ok(tipoSensorService.actualizarTipoSensor(id, tipoSensor));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarTipoSensor(@PathVariable Long id) {
        tipoSensorService.eliminarTipoSensor(id);
        return ResponseEntity.noContent().build();
    }
}
