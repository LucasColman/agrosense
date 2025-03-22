package com.unam.agrosense.model.actuador;


import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuador;
import com.unam.agrosense.model.cambioActuador.CambioActuador;
import com.unam.agrosense.model.dispositivo.Dispositivo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "actuadores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Incluye los atributos de la clase padre en el cálculo del hash
public class Actuador extends Dispositivo {

//    @ManyToMany(mappedBy = "actuadores")
//    private List<TipoActuador> tiposActuadores = new ArrayList<>();

    @OneToMany(mappedBy = "actuador")
    private List<ActuadorTipoActuador> actuadorTipoActuadores;

    @OneToMany(mappedBy = "actuador",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CambioActuador> cambiosActuador;

}
