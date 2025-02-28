package com.unam.agrosense.model.dispositivo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dispositivos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
public abstract class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "latitud", nullable = false)
    private String latitud;

    @Column(name = "longitud", nullable = false)
    private String longitud;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoDispositivo tipoDispositivo;

    private Boolean activo;

}



