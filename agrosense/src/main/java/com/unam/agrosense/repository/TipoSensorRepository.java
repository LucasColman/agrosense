package com.unam.agrosense.repository;

import com.unam.agrosense.model.tipoSensor.TipoSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoSensorRepository extends JpaRepository<TipoSensor, Long> {

    Optional<TipoSensor> findByIdAndActivoTrue(Long id);

    List<TipoSensor> findByActivoTrue();

    @Modifying
    @Query("UPDATE TipoSensor t SET t.activo = false WHERE t.id = :id")
    void softDelete(Long id);
}
