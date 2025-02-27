package com.unam.agrosense;

import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-sensores")
public class TipoSensorController {
    @Autowired
    private TipoSensorService tipoSensorService;

    @PostMapping
    public ResponseEntity<TipoSensor> registrarTipoSensor(@RequestBody TipoSensor tipoSensor) {
        return ResponseEntity.ok(tipoSensorService.registrarTipoSensor(tipoSensor));
    }

    @GetMapping
    public ResponseEntity<List<TipoSensor>> obtenerTiposSensores() {
        return ResponseEntity.ok(tipoSensorService.obtenerTiposSensores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoSensor> obtenerTipoSensor(@PathVariable Long id) {
        return tipoSensorService.obtenerTipoSensor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/tipo-medida")
    public ResponseEntity<TipoSensor> actualizarTipoMedida(
            @PathVariable Long id,
            @RequestBody TipoMedida nuevaMedida) {
        return ResponseEntity.ok(tipoSensorService.actualizarTipoMedida(id, nuevaMedida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoSensor(@PathVariable Long id) {
        tipoSensorService.eliminarTipoSensor(id);
        return ResponseEntity.noContent().build();
    }
}
