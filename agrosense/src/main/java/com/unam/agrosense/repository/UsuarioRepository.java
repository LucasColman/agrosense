package com.unam.agrosense.repository;

import com.unam.agrosense.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
    Usuario findByUsernameOrEmail(String username, String email);
    List<Usuario> findByIdIn(List<Long> userIds);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Optional<Usuario> findByIdAndActivoTrue(Long id);

    @Modifying
    @Query("UPDATE Usuario u SET u.activo = false WHERE u.id = :id")
    void softDelete(Long id);

    List<Usuario> findAllByActivoTrue();
}
