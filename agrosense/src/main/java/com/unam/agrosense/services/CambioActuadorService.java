package com.unam.agrosense.services;

import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.cambioActuador.CambioActuador;
import com.unam.agrosense.model.cambioActuador.CambioActuadorDto;
import com.unam.agrosense.model.cambioActuador.CambioActuadorResponseDto;
import com.unam.agrosense.repository.ActuadorRepository;
import com.unam.agrosense.repository.CambioActuadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CambioActuadorService {
    private final CambioActuadorRepository cambioActuadorRepository;
    private final ActuadorRepository actuadorRepository;

    public CambioActuadorService(CambioActuadorRepository cambioActuadorRepository, ActuadorRepository actuadorRepository) {
        this.cambioActuadorRepository = cambioActuadorRepository;
        this.actuadorRepository = actuadorRepository;
    }

    @Transactional
    public CambioActuadorResponseDto crearCambioActuador(CambioActuadorDto cambioActuadorDto) {

        //Verificar que el cambio ya este creado para evitar duplicar



        if (cambioActuadorDto == null) {
            // Si el DTO es null, no guardamos el cambio
            return null;
        }

        Actuador actuador = actuadorRepository.findById(cambioActuadorDto.actuadorId())
                .orElseThrow(() -> new RuntimeException("Actuador no encontrado"));

        CambioActuador cambioActuador = new CambioActuador();
        cambioActuador.setEstadoAnterior(cambioActuadorDto.estadoAnterior());
        cambioActuador.setEstadoNuevo(cambioActuadorDto.estadoNuevo());
        cambioActuador.setFechaCambio(cambioActuadorDto.fechaCambio());
        cambioActuador.setActuador(actuador);
        cambioActuadorRepository.save(cambioActuador);

        return new CambioActuadorResponseDto(
                cambioActuador.getId(),
                cambioActuador.getEstadoAnterior(),
                cambioActuador.getEstadoNuevo(),
                cambioActuador.getFechaCambio(),
                cambioActuador.getActuador().getId()
        );
    }

    //Update de camnios de actuador
    @Transactional
    public CambioActuadorResponseDto actualizarCambioActuador(Long id, CambioActuadorDto cambioActuadorDto) {
        CambioActuador cambioActuador = cambioActuadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cambio de actuador no existe"));

        Actuador actuador = actuadorRepository.findById(cambioActuadorDto.actuadorId())
                .orElseThrow(() -> new RuntimeException("Actuador no encontrado"));

        cambioActuador.setEstadoAnterior(cambioActuadorDto.estadoAnterior());
        cambioActuador.setEstadoNuevo(cambioActuadorDto.estadoNuevo());
        cambioActuador.setFechaCambio(cambioActuadorDto.fechaCambio());
        cambioActuador.setActuador(actuador);

        cambioActuadorRepository.save(cambioActuador);

        return new CambioActuadorResponseDto(
                cambioActuador.getId(),
                cambioActuador.getEstadoAnterior(),
                cambioActuador.getEstadoNuevo(),
                cambioActuador.getFechaCambio(),
                cambioActuador.getActuador().getId()
        );
    }

    //Delete de cambios de actuador
    @Transactional
    public void eliminarCambioActuador(Long id) {
        CambioActuador cambioActuador = cambioActuadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cambio de actuador no existe"));

        cambioActuadorRepository.softDelete(id);
    }

    //Listar cambios de actuador
    public List<CambioActuadorResponseDto> obtenerCambiosActuadores() {
        List<CambioActuador> cambioActuadores = cambioActuadorRepository.findByActivoTrue();
        return cambioActuadores.stream()
                .map(cambioActuador -> new CambioActuadorResponseDto(
                        cambioActuador.getId(),
                        cambioActuador.getEstadoAnterior(),
                        cambioActuador.getEstadoNuevo(),
                        cambioActuador.getFechaCambio(),
                        cambioActuador.getActuador().getId()
                ))
                .toList();
    }

    //Buscar un cambio de actuador
    public CambioActuadorResponseDto obtenerCambioActuador(Long id) {
        CambioActuador cambioActuador = cambioActuadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cambio de actuador no existe"));

        return new CambioActuadorResponseDto(
                cambioActuador.getId(),
                cambioActuador.getEstadoAnterior(),
                cambioActuador.getEstadoNuevo(),
                cambioActuador.getFechaCambio(),
                cambioActuador.getActuador().getId()
        );

    }
}
