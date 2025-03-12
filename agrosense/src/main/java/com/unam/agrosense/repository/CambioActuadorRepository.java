package com.unam.agrosense.repository;

import com.unam.agrosense.model.cambioActuador.CambioActuador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CambioActuadorRepository extends JpaRepository<CambioActuador, Long> {
    Optional<CambioActuador> findByIdAndActivoTrue(Long id);

    @Modifying // Necesario para realizar modificaciones en la base de datos
    @Query("UPDATE Actuador a SET a.activo = false WHERE a.id = :id")
    void softDelete(Long id);

    boolean existsByIdAndActivoTrue(Long id);

    List<CambioActuador> findByActivoTrue();

    List<CambioActuador> findAllByActivoTrue();
}
