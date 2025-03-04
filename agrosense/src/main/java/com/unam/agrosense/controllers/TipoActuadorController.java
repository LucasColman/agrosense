package com.unam.agrosense.controllers;

import com.unam.agrosense.model.tipoActuador.TipoActuadorResponseDto;
import com.unam.agrosense.services.TipoActuadorService;
import com.unam.agrosense.model.tipoActuador.Comportamiento;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tipos-actuadores")
public class TipoActuadorController {
    @Autowired
    private TipoActuadorService tipoActuadorService;

    @PostMapping("/store")
    public ResponseEntity<TipoActuador> registrarTipoActuador(@ModelAttribute TipoActuador tipoActuador) {
        return ResponseEntity.ok(tipoActuadorService.registrarTipoActuador(tipoActuador));
    }

    @GetMapping
    public String listarTiposActuadores(Model model) {
        List<TipoActuadorResponseDto> tiposActuadores = tipoActuadorService.obtenerTiposActuadores();
        model.addAttribute("tiposActuadores", tiposActuadores);
        model.addAttribute("comportamientos", Comportamiento.values());
        return "dispositivos/TipoActuadores";
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoActuador> obtenerTipoActuador(@PathVariable Long id) {
        return tipoActuadorService.obtenerTipoActuador(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TipoActuador> actualizarComportamiento(
            @PathVariable Long id,
            @RequestBody Comportamiento nuevoComportamiento) {
        return ResponseEntity.ok(tipoActuadorService.actualizarComportamiento(id, nuevoComportamiento));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarTipoActuador(@PathVariable Long id) {
        tipoActuadorService.eliminarTipoActuador(id);
        return ResponseEntity.noContent().build();
    }

}
