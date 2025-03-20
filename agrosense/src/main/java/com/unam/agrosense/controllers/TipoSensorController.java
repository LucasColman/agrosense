package com.unam.agrosense.controllers;

import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.model.tipoSensor.TipoSensorResponseDto;
import com.unam.agrosense.services.TipoSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tipos-sensores")
public class TipoSensorController {
    @Autowired
    private TipoSensorService tipoSensorService;

//    @PostMapping("/store")
//    public ResponseEntity<TipoSensor> registrarTipoSensor(@ModelAttribute TipoSensor tipoSensor) {
//        return ResponseEntity.ok(tipoSensorService.registrarTipoSensor(tipoSensor));
//    }

    @PostMapping("/store")
    public String registrarTipoSensor(@ModelAttribute @Valid TipoSensor tipoSensor,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            capturarErrores(bindingResult,redirectAttributes);
            return "redirect:/tipos-sensores";
        }

        try {
            tipoSensorService.registrarTipoSensor(tipoSensor);
            redirectAttributes.addFlashAttribute("mensaje", "Tipo de sensor registrado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");

        } catch (Exception e) {
            System.out.println("Error al registrar el Tipo de sensor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Error al registrar el Tipo de sensor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }

        return "redirect:/tipos-sensores";
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

//    @PutMapping("/edit/{id}")
//    public ResponseEntity<TipoSensor> actualizarTipoSensor(@ModelAttribute @RequestBody TipoSensor tipoSensor, @PathVariable Long id) {
//        return ResponseEntity.ok(tipoSensorService.actualizarTipoSensor(id, tipoSensor));
//    }

    @PutMapping("/edit/{id}")
    public String actualizarTipoSensor(@ModelAttribute @RequestBody TipoSensor tipoSensor, @PathVariable Long id, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            capturarErrores(bindingResult,redirectAttributes);
            return "redirect:/tipos-sensores";
        }

        try{
            tipoSensorService.actualizarTipoSensor(id, tipoSensor);
            redirectAttributes.addFlashAttribute("mensaje", "Tipo de sensor actualizado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        }catch (Exception e){
            System.out.println("Error al actualizar el tipo de sensor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el tipo de sensor.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");

        }
        return "redirect:/tipos-sensores";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarTipoSensor(@PathVariable Long id) {
        tipoSensorService.eliminarTipoSensor(id);
        return ResponseEntity.noContent().build();
    }


    public void capturarErrores(BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // Captura los mensajes de error y los agrega a los atributos de redirección
        List<String> errores = bindingResult.getFieldErrors().stream()
                .map(error -> String.format("El campo %s %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("errores", errores);
        redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
    }
}
