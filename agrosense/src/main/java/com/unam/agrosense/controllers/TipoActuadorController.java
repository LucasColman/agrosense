package com.unam.agrosense.controllers;

import com.unam.agrosense.model.tipoActuador.TipoActuadorResponseDto;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.services.TipoActuadorService;
import com.unam.agrosense.model.tipoActuador.Comportamiento;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
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
@RequestMapping("/tipos-actuadores")
public class TipoActuadorController {
    @Autowired
    private TipoActuadorService tipoActuadorService;

//    @PostMapping("/store")
//    public ResponseEntity<TipoActuador> registrarTipoActuador(@ModelAttribute TipoActuador tipoActuador) {
//        return ResponseEntity.ok(tipoActuadorService.registrarTipoActuador(tipoActuador));
//    }

    @PostMapping("/store")
    public String registrarTipoActuador(@ModelAttribute @Valid TipoActuador tipoActuador,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            capturarErrores(bindingResult,redirectAttributes);
            return "redirect:/tipos-actuadores"; // Redirige para mostrar el mensaje en la vista
        }

        try {
            tipoActuadorService.registrarTipoActuador(tipoActuador);
            redirectAttributes.addFlashAttribute("mensaje", "Tipo de actuador registrado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");

        } catch (Exception e) {
            System.out.println("Error al registrar el Tipo de actuador: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Error al registrar el Tipo de actuador.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/tipos-actuadores";
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
    public String actualizarTipoActuador(@PathVariable Long id, @ModelAttribute @Valid TipoActuador tipoActuador,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            capturarErrores(bindingResult,redirectAttributes);
            return "redirect:/tipos-actuadores"; // Redirige para mostrar el mensaje en la vista
        }

        try {
            tipoActuadorService.actualizarTipoActuador(id,tipoActuador);
            redirectAttributes.addFlashAttribute("mensaje", "Tipo de actuador actualizado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");

        } catch (Exception e) {
            System.out.println("Error al actualizar el Tipo de actuador: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el Tipo de actuador.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/tipos-actuadores";
    }

//    @PutMapping("/edit/{id}")
//    public ResponseEntity<TipoActuador> actualizarTipoActuador(@PathVariable Long id, @ModelAttribute @RequestBody TipoActuador tipoActuador) {
//        System.out.println("Estados recibidos: " + tipoActuador.getEstados());
//        return ResponseEntity.ok(tipoActuadorService.actualizarTipoActuador(id, tipoActuador));
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarTipoActuador(@PathVariable Long id) {
        tipoActuadorService.eliminarTipoActuador(id);
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
