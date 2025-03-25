package com.unam.agrosense.model.actuadorTipoActuador;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ActuadorTipoActuadorId implements Serializable {
    @Column(name = "actuador_id")
    private Long actuadorId;

    @Column(name = "tipo_actuador_id")
    private Long tipoActuadorId;

}
