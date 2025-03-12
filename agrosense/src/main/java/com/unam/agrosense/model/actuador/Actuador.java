package com.unam.agrosense.model.actuador;


import com.unam.agrosense.model.cambioActuador.CambioActuador;
import com.unam.agrosense.model.dispositivo.Dispositivo;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actuadores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Incluye los atributos de la clase padre en el cálculo del hash
public class Actuador extends Dispositivo {

    @ManyToMany(mappedBy = "actuadores")
    private List<TipoActuador> tiposActuadores = new ArrayList<>();

    @OneToMany(mappedBy = "actuador",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CambioActuador> cambiosActuador;

    private String estadoActuador;

    public void addTipoActuador(TipoActuador tipoActuador) {
        this.tiposActuadores.add(tipoActuador);
    }

    public void removeTipoActuador(TipoActuador tipoActuador) {
        this.tiposActuadores.remove(tipoActuador);
    }
}
