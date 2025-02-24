package com.unam.agrosense;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "DatoSensor")
@Table(name = "datos_sensores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class DatoSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    float valor;

    @Column(nullable = false)
    LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    Sensor sensor;
}
