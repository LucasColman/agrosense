package com.unam.agrosense.repository;

import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuador;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActuadorTipoActuadorRepository extends JpaRepository<ActuadorTipoActuador, ActuadorTipoActuadorId> {
}
