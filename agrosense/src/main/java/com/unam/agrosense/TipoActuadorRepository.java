package com.unam.agrosense;

import com.unam.agrosense.model.tipoActuador.TipoActuador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoActuadorRepository extends JpaRepository<TipoActuador, Long> {

}
