package com.unam.agrosense.services;

import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.actuador.ActuadorDto;
import com.unam.agrosense.model.actuador.ActuadorResponseDto;
import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.sensor.SensorResponseDto;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.repository.ActuadorRepository;
import com.unam.agrosense.repository.TipoActuadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActuadorService {

    private final ActuadorRepository actuadorRepository;
    private final TipoActuadorRepository tipoActuadorRepository;

    public ActuadorService(ActuadorRepository actuadorRepository, TipoActuadorRepository tipoActuadorRepository) {
        this.actuadorRepository = actuadorRepository;
        this.tipoActuadorRepository = tipoActuadorRepository;
    }


    //CREAR UN ACTUADOR
    public ActuadorResponseDto crearActuador(ActuadorDto actuadorDto){
        List<TipoActuador> tiposDeActuadores = tipoActuadorRepository.findAllById(actuadorDto.idsTipoActuador());

        Actuador actuador = new Actuador();

        actuador.setNombre(actuadorDto.nombre());
        actuador.setModelo(actuadorDto.modelo());
        actuador.setLatitud(actuadorDto.latitud());
        actuador.setLongitud(actuadorDto.longitud());
        actuador.setDescripcion(actuadorDto.descripcion());
        actuador.setTipoDispositivo(actuadorDto.tipo());
        actuador.getTiposActuadores().addAll(tiposDeActuadores);

        actuadorRepository.save(actuador);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion(),
                actuador.getTipoDispositivo(),
                actuador.getTiposActuadores()
        );

    }
    //ACTUALIZAR UN ACTUADOR
    public ActuadorResponseDto actualizarActuador(Long id, ActuadorDto actuadorDto){
        Actuador actuador = actuadorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(EntityNotFoundException::new);

        List<TipoActuador> tiposDeActuadores = tipoActuadorRepository.findAllById(actuadorDto.idsTipoActuador());

        actuador.setNombre(actuadorDto.nombre());
        actuador.setModelo(actuadorDto.modelo());
        actuador.setLatitud(actuadorDto.latitud());
        actuador.setLongitud(actuadorDto.longitud());
        actuador.setDescripcion(actuadorDto.descripcion());
        actuador.setTipoDispositivo(actuadorDto.tipo());
        actuador.getTiposActuadores().clear();
        actuador.getTiposActuadores().addAll(tiposDeActuadores);


        actuadorRepository.save(actuador);

        return new ActuadorResponseDto(
                actuador.getId(),
                actuador.getNombre(),
                actuador.getModelo(),
                actuador.getLatitud(),
                actuador.getLongitud(),
                actuador.getDescripcion(),
                actuador.getTipoDispositivo(),
                actuador.getTiposActuadores()
        );
    }

    //ELIMINAR UN ACTUADOR
    @Transactional
    public void eliminarActuador(Long id){

        if(!actuadorRepository.existsByIdAndActivoTrue(id)) {
            throw new RuntimeException("El actuador no existe");
        }
        actuadorRepository.softDelete(id);
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
                actuador.getTipoDispositivo(),
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
                        actuador.getTipoDispositivo(),
                        actuador.getTiposActuadores()
                ))
                .toList();
    }

}
