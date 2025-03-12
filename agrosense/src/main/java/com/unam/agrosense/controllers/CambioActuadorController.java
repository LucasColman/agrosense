package com.unam.agrosense.controllers;

import com.unam.agrosense.model.actuador.ActuadorResponseDto;
import com.unam.agrosense.model.cambioActuador.CambioActuadorDto;
import com.unam.agrosense.model.cambioActuador.CambioActuadorResponseDto;
import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.services.CambioActuadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/cambios-actuadores")
public class CambioActuadorController {
    private final CambioActuadorService cambioActuadorService;

    public CambioActuadorController(CambioActuadorService cambioActuadorService) {
        this.cambioActuadorService = cambioActuadorService;
    }

    //REGISTRAR UN CAMBIO DE ACTUADOR
    @PostMapping("/store")
    public ResponseEntity<CambioActuadorResponseDto> registrarCambioActuador(@ModelAttribute @RequestBody @Valid CambioActuadorDto cambioActuadorDto, UriComponentsBuilder uriBuilder){

        CambioActuadorResponseDto cambioActuadorResponseDto = cambioActuadorService.crearCambioActuador(cambioActuadorDto);

        URI url = uriBuilder.path("/cambios-actuadores/{id}").buildAndExpand(cambioActuadorResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(cambioActuadorResponseDto);
    }

    // ACTUALIZAR UN CAMBIO DE ACTUADOR
    @PutMapping("/edit/{id}")
    public ResponseEntity<CambioActuadorResponseDto> actualizarCambioActuador(@ModelAttribute @RequestBody @Valid CambioActuadorDto cambioActuadorDto, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
        CambioActuadorResponseDto cambioActuadorResponseDto = cambioActuadorService.actualizarCambioActuador(id, cambioActuadorDto);

        return ResponseEntity.ok(cambioActuadorResponseDto);
    }

    // ELIMINAR UN CAMBIO DE ACTUADOR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarCambioActuador(@PathVariable Long id) {
        cambioActuadorService.eliminarCambioActuador(id);
        return ResponseEntity.noContent().build();
    }

    // OBTENER TODOS LOS CAMBIOS DE ACTUADORES
    @GetMapping
    public String listarCambiosActuadores(Model model) {
        List<CambioActuadorResponseDto> cambiosActuadores = cambioActuadorService.obtenerCambiosActuadores();

        model.addAttribute("cambiosActuadores", cambiosActuadores);
        return "dispositivos/CambiosActuadores";
    }

    // OBTENER UN CAMBIO DE ACTUADOR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<CambioActuadorResponseDto> obtenerCambioActuadorPorId(@PathVariable Long id) {
        CambioActuadorResponseDto cambioActuador = cambioActuadorService.obtenerCambioActuador(id);
        return ResponseEntity.ok(cambioActuador);
    }
}
