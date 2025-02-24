package com.unam.agrosense.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "actuadores")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Incluye los atributos de la clase padre en el cálculo del hash
public class Actuador extends Dispositivo {

    @ManyToMany(mappedBy = "actuadores")
    private List<TipoActuador> tiposActuadores;

    @OneToMany(mappedBy = "actuador",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CambioActuador> cambiosActuador;

}
