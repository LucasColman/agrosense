package com.unam.agrosense;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "TipoSensor")
@Table(name = "tipos_sensores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor

public class TipoSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(
        name = "sensores_x_tipos_sensores",
        joinColumns = @JoinColumn(name = "tipo_sensor_id"),
        inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    @Column(nullable = false)
    List<Sensor> sensores;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoMedida tipoMedida;
}
