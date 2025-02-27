package com.unam.agrosense;

import com.unam.agrosense.model.tipoSensor.TipoSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSensorRepository extends JpaRepository<TipoSensor, Long> {
    boolean existsByNombre(String nombre);

}
