package com.unam.agrosense.model.tipoSensor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unam.agrosense.model.sensor.Sensor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tipos_sensores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMedida tipoMedida;

    private boolean activo = true;

    @ManyToMany
    @JoinTable(
        name = "sensores_x_tipos_sensores",
        joinColumns = @JoinColumn(name = "tipo_sensor_id"),
        inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    @JsonIgnore
    private List<Sensor> sensores;


}
