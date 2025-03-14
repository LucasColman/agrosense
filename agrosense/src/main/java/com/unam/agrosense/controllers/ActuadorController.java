package com.unam.agrosense.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.*;

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
    public ResponseEntity<ActuadorResponseDto> registrarActuador(@ModelAttribute @RequestBody @Valid ActuadorDto actuadorDto, UriComponentsBuilder uriBuilder){

        ActuadorResponseDto actuadorResponseDto = actuadorService.crearActuador(actuadorDto);

        URI url = uriBuilder.path("/actuadores/{id}").buildAndExpand(actuadorResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(actuadorResponseDto);
    }


    // ACTUALIZAR UN ACTUADOR
    @PutMapping("/edit/{id}")
    public ResponseEntity<ActuadorResponseDto> actualizarActuador(@ModelAttribute @RequestBody @Valid ActuadorDto actuadorDto, @PathVariable Long id, UriComponentsBuilder uriBuilder) {
        ActuadorResponseDto actuadorResponseDto = actuadorService.actualizarActuador(id, actuadorDto);

        return ResponseEntity.ok(actuadorResponseDto);
    }

    @PutMapping("/edit/estado/{id}")
    public ResponseEntity<ActuadorResponseDto> actualizarEstadoActuador(@PathVariable Long id, @RequestParam String estado) {
        ActuadorResponseDto actuadorResponseDto = actuadorService.actualizarEstadoActuador(id, estado);
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

        Map<Long, List<String>> estadosPorActuador = new HashMap<>();

        for (ActuadorResponseDto actuador : actuadores) {
            List<String> estadosPosibles = actuador.tiposDeActuadores()
                    .stream()
                    .flatMap(tipo -> tipo.getEstados().stream())
                    .distinct()
                    .toList();

            // Serializar la lista de estados como JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String estadosJson = objectMapper.writeValueAsString(estadosPosibles);
                estadosPorActuador.put(actuador.id(), Collections.singletonList(estadosJson));
            } catch (Exception e) {
                e.printStackTrace(); // Manejo de error si la serialización falla
            }
        }

        System.out.println(estadosPorActuador);

        model.addAttribute("actuadores", actuadores);
        model.addAttribute("tiposDispositivo", TipoDispositivo.values());
        model.addAttribute("tiposActuadores", tipoActuadorService.obtenerTiposActuadores());
        model.addAttribute("estadosPorActuador", estadosPorActuador);
        return "dispositivos/Actuadores";
    }


    @GetMapping("/cantidad")
    public ResponseEntity<Integer> cantidadDeActuadores() {
        return ResponseEntity.ok().body(actuadorService.cantidadDeActuadores());
    }
}
