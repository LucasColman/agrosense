package com.unam.agrosense.model.sensor;

import com.unam.agrosense.model.DatoSensor;
import com.unam.agrosense.model.dispositivo.Dispositivo;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sensores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Sensor extends Dispositivo {

    @ManyToMany(mappedBy = "sensores")
    private List<TipoSensor> tiposSensores = new ArrayList<>();

    @OneToMany(mappedBy = "sensor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DatoSensor> datosSensor;

    public void addTipoSensor(TipoSensor tipoSensor) {
        this.tiposSensores.add(tipoSensor);
    }
}
