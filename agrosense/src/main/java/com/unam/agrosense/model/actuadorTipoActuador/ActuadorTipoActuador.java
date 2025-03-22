package com.unam.agrosense.model.actuadorTipoActuador;

import com.unam.agrosense.model.actuador.Actuador;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actuadores_x_tipos_actuadores")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ActuadorTipoActuador {

    @EmbeddedId
    private ActuadorTipoActuadorId id;

    @ManyToOne
    @MapsId("tipoActuadorId")
    @JoinColumn(name = "tipo_actuador_id")
    private TipoActuador tipoActuador;

    @ManyToOne
    @MapsId("actuadorId")
    @JoinColumn(name = "actuador_id")
    private Actuador actuador;

    private String estadoActual; // Estado actual de la relacion entre el actuador y el tipo de actuador
}
