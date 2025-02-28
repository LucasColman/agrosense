package com.unam.agrosense.repository;

import com.unam.agrosense.model.dispositivo.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
}
