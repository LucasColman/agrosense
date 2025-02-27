package com.unam.agrosense;

import com.unam.agrosense.model.tipoActuador.Comportamiento;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-actuadores")
public class TipoActuadorController {
    @Autowired
    private TipoActuadorService tipoActuadorService;

    @PostMapping
    public ResponseEntity<TipoActuador> registrarTipoActuador(@RequestBody TipoActuador tipoActuador) {
        return ResponseEntity.ok(tipoActuadorService.registrarTipoActuador(tipoActuador));
    }

    @GetMapping
    public ResponseEntity<List<TipoActuador>> obtenerTiposActuadores() {
        return ResponseEntity.ok(tipoActuadorService.obtenerTiposActuadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoActuador> obtenerTipoActuador(@PathVariable Long id) {
        return tipoActuadorService.obtenerTipoActuador(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/comportamiento")
    public ResponseEntity<TipoActuador> actualizarComportamiento(
            @PathVariable Long id,
            @RequestBody Comportamiento nuevoComportamiento) {
        return ResponseEntity.ok(tipoActuadorService.actualizarComportamiento(id, nuevoComportamiento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoActuador(@PathVariable Long id) {
        tipoActuadorService.eliminarTipoActuador(id);
        return ResponseEntity.noContent().build();
    }

}
