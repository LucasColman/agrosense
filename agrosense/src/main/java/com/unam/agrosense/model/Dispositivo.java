package com.unam.agrosense.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dispositivos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "latitud", nullable = false)
    private String latitud;
    @Column(name = "longitud", nullable = false)
    private String longitud;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "tipo_dispositivo", nullable = false)
    private TipoDispositivo tipoDispositivo;
}



