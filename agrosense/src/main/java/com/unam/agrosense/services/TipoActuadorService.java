package com.unam.agrosense.services;

import com.unam.agrosense.model.tipoActuador.Comportamiento;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import com.unam.agrosense.repository.TipoActuadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoActuadorService {
    @Autowired
    private TipoActuadorRepository tipoActuadorRepository;

    public TipoActuador registrarTipoActuador(TipoActuador tipoActuador) {
        return tipoActuadorRepository.save(tipoActuador);
    }

    public List<TipoActuador> obtenerTiposActuadores() {
        return tipoActuadorRepository.findAll();
    }

    public Optional<TipoActuador> obtenerTipoActuador(Long id) {
        return tipoActuadorRepository.findById(id);
    }

    public TipoActuador actualizarComportamiento(Long id, Comportamiento nuevoComportamiento) {
        return tipoActuadorRepository.findById(id).map(tipoActuador -> {
            tipoActuador.setComportamiento(nuevoComportamiento);
            return tipoActuadorRepository.save(tipoActuador);
        }).orElseThrow(() -> new IllegalArgumentException("Tipo de actuador no encontrado"));
    }

    public void eliminarTipoActuador(Long id) {
        if(!tipoActuadorRepository.existsById(id)) {
            throw new RuntimeException("El tipo de actuador no existe");
        }
        tipoActuadorRepository.deleteById(id);
    }

}
