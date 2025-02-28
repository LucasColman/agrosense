package com.unam.agrosense.model;

import com.unam.agrosense.model.actuador.Actuador;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cambios_actuadores")
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CambioActuador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado_anterior", nullable = false)
    private String estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false)
    private String estadoNuevo;

    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;

    @ManyToOne
    @JoinColumn(name = "actuador_id")
    private Actuador actuador;

}
