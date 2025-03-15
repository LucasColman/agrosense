package com.unam.agrosense.services;

import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.actuador.ActuadorDto;
import com.unam.agrosense.model.actuador.ActuadorResponseDto;

import com.unam.agrosense.model.cambioActuador.CambioActuadorDto;
import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.sensor.SensorResponseDto;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.repository.ActuadorRepository;
import com.unam.agrosense.repository.TipoActuadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActuadorService {

    private final ActuadorRepository actuadorRepository;
    private final TipoActuadorRepository tipoActuadorRepository;
    private final CambioActuadorService cambioActuadorService;

    public ActuadorService(ActuadorRepository actuadorRepository, TipoActuadorRepository tipoActuadorRepository, CambioActuadorService cambioActuadorService) {
        this.actuadorRepository = actuadorRepository;
        this.tipoActuadorRepository = tipoActuadorRepository;
        this.cambioActuadorService = cambioActuadorService;
    }


    //CREAR UN ACTUADOR
    @Transactional
    public ActuadorResponseDto crearActuador(ActuadorDto actuadorDto) {
        List<TipoActuador> tiposDeActuadores = tipoActuadorRepository.findAllById(actuadorDto.idsTipoActuador());

        if (actuadorRepository.existsByNombreAndActivoTrue(actuadorDto.nombre())) {
            throw new RuntimeException("El actuador ya existe");
        }

        Actuador actuador = new Actuador();
        actuador.setNombre(actuadorDto.nombre());
        actuador.setModelo(actuadorDto.modelo());
        actuador.setLatitud(actuadorDto.latitud());
        actuador.setLongitud(actuadorDto.longitud());
        actuador.setDescripcion(actuadorDto.descripcion());
        actuador.setEstadoActuador("No definido");
        actuador.setTipoDispositivo(TipoDispositivo.ACTUADOR);
        actuador.getTiposActuadores().addAll(tiposDeActuadores);
        tiposDeActuadores.forEach(tipoActuador -> tipoActuador.getActuadores().add(actuador));

        actuadorRepository.save(actuador);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion(),
                actuador.getEstadoActuador(),
                actuador.getTiposActuadores()
        );
    }

    //Actualizar estado de un actuador
    @Transactional
    public ActuadorResponseDto actualizarEstadoActuador(Long id, String estado) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));

        String estadoAnterior = actuador.getEstadoActuador();

        actuador.setEstadoActuador(estado);
        actuadorRepository.save(actuador);

        CambioActuadorDto cambioActuadorDto = new CambioActuadorDto(
                estadoAnterior,
                estado,
                java.time.LocalDateTime.now(),
                id
        );

        cambioActuadorService.crearCambioActuador(cambioActuadorDto);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion(),
                actuador.getEstadoActuador(),
                actuador.getTiposActuadores()
        );
    }

    //ACTUALIZAR UN ACTUADOR
    @Transactional
    public ActuadorResponseDto actualizarActuador(Long id, ActuadorDto actuadorDto) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));

        List<TipoActuador> tiposDeActuadores = actuadorDto.idsTipoActuador() != null ?
                tipoActuadorRepository.findAllById(actuadorDto.idsTipoActuador()) :
                List.of();

        actuador.setNombre(actuadorDto.nombre());
        actuador.setModelo(actuadorDto.modelo());
        actuador.setLatitud(actuadorDto.latitud());
        actuador.setLongitud(actuadorDto.longitud());
        actuador.setDescripcion(actuadorDto.descripcion());
        actuador.setEstadoActuador(actuadorDto.estadoActuador());

        if (!tiposDeActuadores.isEmpty()) {
            actuador.getTiposActuadores().forEach(
                    tipoActuador -> tipoActuador.getActuadores().remove(actuador)
            );
            actuador.getTiposActuadores().clear();

            tiposDeActuadores.forEach(tipoActuador -> {
                actuador.getTiposActuadores().add(tipoActuador);
                tipoActuador.getActuadores().add(actuador);
            });
        }
        actuadorRepository.save(actuador);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion(),
                actuador.getEstadoActuador(),
                actuador.getTiposActuadores()
        );
    }


    //ELIMINAR UN ACTUADOR
    @Transactional
    public void eliminarActuador(Long id) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("El actuador no existe"));

        actuadorRepository.softDelete(id);
        for (TipoActuador tipoActuador : actuador.getTiposActuadores()) {
            tipoActuador.getActuadores().remove(actuador);
        }
        actuador.getTiposActuadores().clear();

    }


    //OBTENER UN ACTUADOR
    public ActuadorResponseDto obteneractuador(Long id) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("El actuador no existe"));

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion(),
                actuador.getEstadoActuador(),
                actuador.getTiposActuadores()
        );

    }

    //OBTENER TODOS LOS ACTUADORES
    public List<ActuadorResponseDto> obtenerActuadores() {
        List<Actuador> actuadores = actuadorRepository.findByActivoTrue();
        return actuadores.stream()
                .map(actuador -> new ActuadorResponseDto(
                        actuador.getId(),
                        actuador.getNombre(),
                        actuador.getModelo(),
                        actuador.getLatitud(),
                        actuador.getLongitud(),
                        actuador.getDescripcion(),
                        actuador.getEstadoActuador(),
                        actuador.getTiposActuadores()
                ))
                .toList();
    }

    public int cantidadDeActuadores() {
        var actuatorCount = 0;
        List<Actuador> actuadores =  actuadorRepository.findAllByActivoTrue();
        actuatorCount = actuadores.size();
        return actuatorCount;
    }


    //Modificar estado de actuador y luego crear un cambio actuador
    @Transactional
    public CambioActuadorDto modificarEstadoActuador(Long id, String estadoActuador) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));

        String estadoAnterior = actuador.getEstadoActuador();

        // Verificar si el estado ha cambiado
        if (!estadoAnterior.equals(estadoActuador)) {
            actuador.setEstadoActuador(estadoActuador);
            actuadorRepository.save(actuador);

            // Solo si el estado ha cambiado, retornamos el DTO
            return new CambioActuadorDto(
                    estadoAnterior,
                    estadoActuador,
                    LocalDateTime.now(),
                    actuador.getId()
            );
        } else {
            // Si el estado no cambia, devolvemos null
            return null;
        }




    }
}