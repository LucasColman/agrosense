package com.unam.agrosense.model.cambioActuador;

import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuador;
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

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "estado_anterior", nullable = false)
    private String estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false)
    private String estadoNuevo;

    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;

//    @ManyToOne
//    @JoinColumn(name = "actuador_id")
//    private Actuador actuador;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "actuador_id", referencedColumnName = "actuador_id"),
            @JoinColumn(name = "tipo_actuador_id", referencedColumnName = "tipo_actuador_id")
    })
    private ActuadorTipoActuador actuadorTipoActuador;

}
