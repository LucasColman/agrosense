package com.unam.agrosense.controllers;

import com.unam.agrosense.model.actuador.ActuadorDto;
import com.unam.agrosense.model.actuador.ActuadorResponseDto;
import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import com.unam.agrosense.services.ActuadorService;
import com.unam.agrosense.services.TipoActuadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/actuadores")
public class ActuadorController {

    private final ActuadorService actuadorService;
    private final TipoActuadorService tipoActuadorService;

    public ActuadorController(ActuadorService actuadorService, TipoActuadorService tipoActuadorService) {
        this.actuadorService = actuadorService;
        this.tipoActuadorService = tipoActuadorService;
    }


    //REGISTRAR UN ACTUADOR
    @PostMapping("/store")
    public ResponseEntity<ActuadorResponseDto> registrarActuador(@ModelAttribute @Valid ActuadorDto actuadorDto, UriComponentsBuilder uriBuilder){

        ActuadorResponseDto actuadorResponseDto = actuadorService.crearActuador(actuadorDto);

        URI url = uriBuilder.path("/actuadores/{id}").buildAndExpand(actuadorResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(actuadorResponseDto);
    }


    // ACTUALIZAR UN ACTUADOR
    @PutMapping("/{id}")
    public ResponseEntity<ActuadorResponseDto> actualizarActuador(@RequestBody @Valid ActuadorDto actuadorDto, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
        ActuadorResponseDto actuadorResponseDto = actuadorService.actualizarActuador(id, actuadorDto);

        return ResponseEntity.ok(actuadorResponseDto);
    }


    // ELIMINAR UN ACTUADOR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarActuador(@PathVariable Long id) {
        actuadorService.eliminarActuador(id);
        return ResponseEntity.noContent().build();
    }


    // OBTENER UN ACTUADOR

    @GetMapping("/{id}")
    public ResponseEntity<ActuadorResponseDto> obtenerActuador(@PathVariable Long id) {
        ActuadorResponseDto actuadorResponseDto = actuadorService.obteneractuador(id);
        return ResponseEntity.ok(actuadorResponseDto);
    }

    // OBTENER TODOS LOS ACTUADORES
    @GetMapping
    public String listarActuadores(Model model) {
        List<ActuadorResponseDto> actuadores = actuadorService.obtenerActuadores();


        model.addAttribute("actuadores", actuadores);
        model.addAttribute("tiposDispositivo", TipoDispositivo.values());
        model.addAttribute("tiposActuadores", tipoActuadorService.obtenerTiposActuadores());
        return "dispositivos/Actuadores";
    }


    @GetMapping("/cantidad")
    public ResponseEntity<Integer> cantidadDeActuadores() {
        return ResponseEntity.ok().body(actuadorService.cantidadDeActuadores());
    }
}
