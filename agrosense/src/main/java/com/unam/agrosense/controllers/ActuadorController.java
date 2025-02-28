package com.unam.agrosense.controllers;

import com.unam.agrosense.model.actuador.ActuadorDto;
import com.unam.agrosense.model.actuador.ActuadorResponseDto;
import com.unam.agrosense.services.ActuadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/actuadores")
public class ActuadorController {

    private final ActuadorService actuadorService;

    public ActuadorController(ActuadorService actuadorService) {
        this.actuadorService = actuadorService;
    }


    //REGISTRAR UN ACTUADOR
    @PostMapping
    public ResponseEntity<ActuadorResponseDto> registrarActuador(@RequestBody @Valid ActuadorDto actuadorDto, UriComponentsBuilder uriBuilder){

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
    @DeleteMapping("/{id}")
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
    public ResponseEntity<List<ActuadorResponseDto>> obtenerActuadores() {
        List<ActuadorResponseDto> actuadorResponseDto = actuadorService.obtenerActuadores();

        return ResponseEntity.ok(actuadorResponseDto);
    }
}
