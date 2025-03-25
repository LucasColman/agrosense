package com.unam.agrosense.services;

import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuador;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorId;
import com.unam.agrosense.model.cambioActuador.CambioActuador;
import com.unam.agrosense.model.cambioActuador.CambioActuadorDto;
import com.unam.agrosense.model.cambioActuador.CambioActuadorResponseDto;
import com.unam.agrosense.repository.ActuadorRepository;
import com.unam.agrosense.repository.ActuadorTipoActuadorRepository;
import com.unam.agrosense.repository.CambioActuadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CambioActuadorService {
    private final CambioActuadorRepository cambioActuadorRepository;
    private final ActuadorRepository actuadorRepository;
    private final ActuadorTipoActuadorRepository actuadorTipoActuadorRepository;

    public CambioActuadorService(CambioActuadorRepository cambioActuadorRepository, ActuadorRepository actuadorRepository, ActuadorTipoActuadorRepository actuadorTipoActuadorRepository) {
        this.cambioActuadorRepository = cambioActuadorRepository;
        this.actuadorRepository = actuadorRepository;
        this.actuadorTipoActuadorRepository = actuadorTipoActuadorRepository;
    }

    @Transactional
    public CambioActuadorResponseDto crearCambioActuador(CambioActuadorDto cambioActuadorDto) {

        // Buscar el ActuadorTipoActuador por su ID
        ActuadorTipoActuadorId id = new ActuadorTipoActuadorId(cambioActuadorDto.actuadorId(), cambioActuadorDto.tipoActuadorId());

        ActuadorTipoActuador actuadorTipoActuador = actuadorTipoActuadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ActuadorTipoActuador no encontrado"));


        CambioActuador cambioActuador = new CambioActuador();
        cambioActuador.setEstadoAnterior(cambioActuadorDto.estadoAnterior());
        cambioActuador.setEstadoNuevo(cambioActuadorDto.estadoNuevo());
        cambioActuador.setFechaCambio(cambioActuadorDto.fechaCambio());
        cambioActuador.setActuadorTipoActuador(actuadorTipoActuador);

        cambioActuadorRepository.save(cambioActuador);

        return new CambioActuadorResponseDto(
                cambioActuador.getId(),
                cambioActuador.getEstadoAnterior(),
                cambioActuador.getEstadoNuevo(),
                cambioActuador.getFechaCambio(),
                cambioActuador.getActuadorTipoActuador().getActuador().getNombre(),
                cambioActuador.getActuadorTipoActuador().getTipoActuador().getDescripcion()
        );
    }

    //Update de camnios de actuador
    @Transactional
    public CambioActuadorResponseDto actualizarCambioActuador(Long id, CambioActuadorDto cambioActuadorDto) {
        CambioActuador cambioActuador = cambioActuadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cambio de actuador no existe"));

        // Buscar el ActuadorTipoActuador por su ID
        ActuadorTipoActuadorId idActuadorTipo = new ActuadorTipoActuadorId(cambioActuadorDto.actuadorId(), cambioActuadorDto.tipoActuadorId());

        ActuadorTipoActuador actuadorTipoActuador = actuadorTipoActuadorRepository.findById(idActuadorTipo)
                .orElseThrow(() -> new RuntimeException("ActuadorTipoActuador no encontrado"));

        cambioActuador.setEstadoAnterior(cambioActuadorDto.estadoAnterior());
        cambioActuador.setEstadoNuevo(cambioActuadorDto.estadoNuevo());
        cambioActuador.setFechaCambio(cambioActuadorDto.fechaCambio());
        cambioActuador.setActuadorTipoActuador(actuadorTipoActuador);

        cambioActuadorRepository.save(cambioActuador);

        return new CambioActuadorResponseDto(
                cambioActuador.getId(),
                cambioActuador.getEstadoAnterior(),
                cambioActuador.getEstadoNuevo(),
                cambioActuador.getFechaCambio(),
                cambioActuador.getActuadorTipoActuador().getActuador().getNombre(),
                cambioActuador.getActuadorTipoActuador().getTipoActuador().getDescripcion()
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
                        cambioActuador.getActuadorTipoActuador().getActuador().getNombre(),
                        cambioActuador.getActuadorTipoActuador().getTipoActuador().getDescripcion()
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
                cambioActuador.getActuadorTipoActuador().getActuador().getNombre(),
                cambioActuador.getActuadorTipoActuador().getTipoActuador().getDescripcion()
        );
    }

    public List<ActuadorTipoActuador> obtenerActuadoresTipoActuador() {
        return actuadorTipoActuadorRepository.findAll();
    }



}
