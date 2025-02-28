package com.unam.agrosense.repository;

import com.unam.agrosense.model.actuador.Actuador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActuadorRepository extends JpaRepository<Actuador, Long> {

    Optional<Actuador> findByIdAndActivoTrue(Long id);

    @Modifying // Necesario para realizar modificaciones en la base de datos
    @Query("UPDATE Actuador a SET a.activo = false WHERE a.id = :id")
    void softDelete(Long id);

    boolean existsByIdAndActivoTrue(Long id);

    List<Actuador> findByActivoTrue();
}
