package com.unam.agrosense.model.datoSensor;

import com.unam.agrosense.model.sensor.Sensor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "datos_sensores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DatoSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    private boolean activo = true;

    public Long getSensorId(){
        return this.getSensor().getId();

    }
}
