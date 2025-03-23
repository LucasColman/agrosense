package com.unam.agrosense.services;

import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuador;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorDto;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorId;
import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.actuador.ActuadorDto;
import com.unam.agrosense.model.actuador.ActuadorResponseDto;

import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorResponseDto;
import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import com.unam.agrosense.repository.ActuadorRepository;
import com.unam.agrosense.repository.ActuadorTipoActuadorRepository;
import com.unam.agrosense.repository.TipoActuadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActuadorService {

    private final ActuadorRepository actuadorRepository;
    private final TipoActuadorRepository tipoActuadorRepository;
    private final CambioActuadorService cambioActuadorService;
    private final ActuadorTipoActuadorRepository actuadorTipoActuadorRepository;

    public ActuadorService(ActuadorRepository actuadorRepository, TipoActuadorRepository tipoActuadorRepository, CambioActuadorService cambioActuadorService, ActuadorTipoActuadorRepository actuadorTipoActuadorRepository) {
        this.actuadorRepository = actuadorRepository;
        this.tipoActuadorRepository = tipoActuadorRepository;
        this.cambioActuadorService = cambioActuadorService;
        this.actuadorTipoActuadorRepository = actuadorTipoActuadorRepository;
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
        actuador.setTipoDispositivo(TipoDispositivo.ACTUADOR);
//        actuador.getTiposActuadores().addAll(tiposDeActuadores);
//        tiposDeActuadores.forEach(tipoActuador -> tipoActuador.getActuadores().add(actuador));

        actuadorRepository.save(actuador);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion()
//                actuador.getTiposActuadores()
        );
    }

    //Actualizar estado de un actuador
//    @Transactional
//    public ActuadorResponseDto actualizarEstadoActuador(Long id, String estado) {
//        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
//                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));
//
//        String estadoAnterior = actuador.getEstadoActuador();
//
//        actuador.setEstadoActuador(estado);
//        actuadorRepository.save(actuador);
//
//        CambioActuadorDto cambioActuadorDto = new CambioActuadorDto(
//                estadoAnterior,
//                estado,
//                java.time.LocalDateTime.now(),
//                id
//        );
//
//        cambioActuadorService.crearCambioActuador(cambioActuadorDto);
//
//        return new ActuadorResponseDto(
//                actuador.getId(),
//                actuador.getNombre(),
//                actuador.getModelo(),
//                actuador.getLatitud(),
//                actuador.getLongitud(),
//                actuador.getDescripcion(),
//                actuador.getEstadoActuador(),
//                actuador.getTiposActuadores()
//        );
//    }

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

//        if (!tiposDeActuadores.isEmpty()) {
//            actuador.getTiposActuadores().forEach(
//                    tipoActuador -> tipoActuador.getActuadores().remove(actuador)
//            );
//            actuador.getTiposActuadores().clear();
//
//            tiposDeActuadores.forEach(tipoActuador -> {
//                actuador.getTiposActuadores().add(tipoActuador);
//                tipoActuador.getActuadores().add(actuador);
//            });
//        }
        actuadorRepository.save(actuador);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion()
//                actuador.getTiposActuadores()
        );
    }


    //ELIMINAR UN ACTUADOR
    @Transactional
    public void eliminarActuador(Long id) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("El actuador no existe"));

        actuadorRepository.softDelete(id);
//        for (TipoActuador tipoActuador : actuador.getTiposActuadores()) {
//            tipoActuador.getActuadores().remove(actuador);
//        }
//        actuador.getTiposActuadores().clear();

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
                actuador.getDescripcion()
//                actuador.getTiposActuadores()
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
                        actuador.getDescripcion()
//                        actuador.getTiposActuadores()
                ))
                .toList();
    }
    /*
    public List<ActuadorTipoActuadorResponseDto> obtenerActuadoresTipoActuador(){
        List<ActuadorTipoActuador> actuadoresTipoActuador = actuadorTipoActuadorRepository.findAll();
        return actuadoresTipoActuador.stream()
                .map(actuadorTipoActuador -> new ActuadorTipoActuadorResponseDto(
                        actuadorTipoActuador.getId().getTipoActuadorId(),
                        actuadorTipoActuador.getId().getActuadorId(),
                        actuadorTipoActuador.getEstadoActual()
                ))
                .toList();
    }*/

    public List<ActuadorTipoActuadorResponseDto> obtenerActuadoresTipoActuadorByActuadorId(Long actuadorId) {
        List<ActuadorTipoActuador> actuadoresTipoActuador = actuadorTipoActuadorRepository.findByActuadorId(actuadorId);

        return actuadoresTipoActuador.stream()
                .map(actuadorTipoActuador -> new ActuadorTipoActuadorResponseDto(
                        actuadorTipoActuador.getTipoActuador().getId(),  // Obtener el ID del tipo de actuador
                        actuadorTipoActuador.getActuador().getId(),  // Obtener el ID del actuador
                        actuadorTipoActuador.getEstadoActual(),  // Estado actual
                        actuadorTipoActuador.getTipoActuador().getDescripcion() // Obtener descripción del tipo de actuador
                ))
                .toList();
    }

    public void cambiarEstadoActuadorTipoActuador(Long actuadorId, Long tipoActuadorId, String nuevoEstado) {
        // Buscar la relación en la tabla intermedia
        ActuadorTipoActuadorId id = new ActuadorTipoActuadorId(actuadorId, tipoActuadorId);
        Optional<ActuadorTipoActuador> actuadorTipoActuadorOpt = actuadorTipoActuadorRepository.findById(id);

        if (actuadorTipoActuadorOpt.isPresent()) {
            // Actualizar el estado actual
            ActuadorTipoActuador actuadorTipoActuador = actuadorTipoActuadorOpt.get();
            actuadorTipoActuador.setEstadoActual(nuevoEstado);
            actuadorTipoActuadorRepository.save(actuadorTipoActuador); // Guardar los cambios
        } else {
            throw new RuntimeException("La relación entre actuador y tipo de actuador no existe.");
        }
    }

    public int cantidadDeActuadores() {
        var actuatorCount = 0;
        List<Actuador> actuadores = actuadorRepository.findAllByActivoTrue();
        actuatorCount = actuadores.size();
        return actuatorCount;
    }

    @Transactional
    public void agregarTipoActuador(ActuadorTipoActuadorDto actuadorTipoActuadorDto) {
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(actuadorTipoActuadorDto.actuadorId())
                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));

        TipoActuador tipoActuador = tipoActuadorRepository.findById(actuadorTipoActuadorDto.tipoActuadorId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de actuador no existe"));

        ActuadorTipoActuadorId id = new ActuadorTipoActuadorId(tipoActuador.getId(), actuador.getId());

        ActuadorTipoActuador actuadorTipo = new ActuadorTipoActuador();
        actuadorTipo.setId(id);
        actuadorTipo.setActuador(actuador);
        actuadorTipo.setTipoActuador(tipoActuador);
        actuadorTipo.setEstadoActual(actuadorTipoActuadorDto.estadoActual());

        actuadorTipoActuadorRepository.save(actuadorTipo);
    }




    //Modificar estado de actuador y luego crear un cambio actuador
//    @Transactional
//    public CambioActuadorDto modificarEstadoActuador(Long id, String estadoActuador) {
//        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
//                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));
//
//        String estadoAnterior = actuador.getEstadoActuador();
//
//
//        actuador.setEstadoActuador(estadoActuador);
//        actuadorRepository.save(actuador);
//
//        return new CambioActuadorDto(
//                estadoAnterior,
//                estadoActuador,
//                LocalDateTime.now(),
//                actuador.getId()
//        );
//
//    }

    // OBTENER LOS ESTADOS POSIBLES DE UN ACTUADOR y los nombres de los tipos de actuadores
//    public List<Map.Entry<String, String>> obtenerEstadosPosibles(Long id) {
//        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
//                .orElseThrow(() -> new EntityNotFoundException("Actuador no existe"));
//
//        return actuador.getTiposActuadores()
//                .stream()
//                .flatMap(tipo -> tipo.getEstados().stream()
//                        .map(estado -> Map.entry(tipo.getDescripcion(), estado))) // Asocia estado con tipo de actuador
//                .distinct()
//                .toList();
//    }

}