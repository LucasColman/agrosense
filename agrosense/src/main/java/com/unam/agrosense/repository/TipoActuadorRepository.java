package com.unam.agrosense.repository;

import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoActuadorRepository extends JpaRepository<TipoActuador, Long> {
    @Modifying
    @Query("UPDATE TipoActuador s SET s.activo = false WHERE s.id = :id")
    void softDelete(Long id);

    Optional<TipoActuador> findByIdAndActivoTrue(Long id);

    List<TipoActuador> findByActivoTrue();
}
