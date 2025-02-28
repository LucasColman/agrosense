package com.unam.agrosense.repository;

import com.unam.agrosense.model.tipoSensor.TipoSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoSensorRepository extends JpaRepository<TipoSensor, Long> {


    boolean existsByNombre(String nombre);

}
