package com.unam.agrosense.controllers;


import com.unam.agrosense.model.actuador.ActuadorDto;
import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.sensor.SensorDto;
import com.unam.agrosense.model.sensor.SensorResponseDto;
import com.unam.agrosense.services.SensorService;
import com.unam.agrosense.services.TipoSensorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
//    @PostMapping("/store")
//    public ResponseEntity<SensorResponseDto> registrarSensor(@ModelAttribute @Valid SensorDto sensorDto, UriComponentsBuilder uriBuilder) {
//        SensorResponseDto sensorResponseDto = sensorService.crearSensor(sensorDto);
//
//        URI url = uriBuilder.path("/sensores/{id}").buildAndExpand(sensorResponseDto.id()).toUri();
//        return ResponseEntity.created(url).body(sensorResponseDto);
//    }


    @PostMapping("/store")
    public String registrarSensor(@ModelAttribute @Valid SensorDto sensorDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getFieldErrors().stream()
                    .map(error -> String.format("El campo %s %s", error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            redirectAttributes.addFlashAttribute("errores", errores);
            return "redirect:/sensores"; // Redirige a la misma vista
        }

        try {
            sensorService.crearSensor(sensorDto);
            redirectAttributes.addFlashAttribute("mensaje", "Sensor registrado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/sensores";

        } catch (Exception e) {
            System.out.println("Error al registrar el sensor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Error al registrar el sensor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/actuadores";
    }


    @PutMapping("/edit/{id}")
    public String actualizarSensor(@ModelAttribute @RequestBody @Valid SensorDto sensorDto,
                                     @PathVariable Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            manejarErrores(bindingResult,redirectAttributes);
            return "redirect:/sensores";
        }

        try {
            sensorService.actualizarSensor(id, sensorDto);
            redirectAttributes.addFlashAttribute("mensaje", "Sensor actualizado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            System.out.println("Error al actualizar el Sensor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el Sensor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }

        return "redirect:/sensores";

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


    public void manejarErrores(BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // Captura los mensajes de error y los agrega a los atributos de redirección
        List<String> errores = bindingResult.getFieldErrors().stream()
                .map(error -> String.format("El campo %s %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("errores", errores);
        redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
    }
}
