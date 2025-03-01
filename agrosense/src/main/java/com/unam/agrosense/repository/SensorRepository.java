package com.unam.agrosense.repository;


import com.unam.agrosense.model.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    @Modifying
    @Query("UPDATE Sensor s SET s.activo = false WHERE s.id = :id")
    void softDelete(Long id);

    Optional<Sensor> findByIdAndActivoTrue(Long id);

    List<Sensor> findByActivoTrue();

    List<Sensor> findAllByActivoTrue();

    boolean existsByIdAndActivoTrue(Long id);

    boolean existsByNombreAndActivoTrue(String nombre);


}
