package com.unam.agrosense.repository;

import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuador;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActuadorTipoActuadorRepository extends JpaRepository<ActuadorTipoActuador, ActuadorTipoActuadorId> {
    List<ActuadorTipoActuador> findByActuadorId(Long actuadorId);
}
