package com.unam.agrosense.model.actuadorTipoActuador;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ActuadorTipoActuadorId implements Serializable {
    private Long tipoActuadorId;
    private Long actuadorId;

}
