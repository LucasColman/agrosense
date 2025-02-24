package com.unam.agrosense;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Sensor")
@Table(name = "sensores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Sensor extends Dispositivo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToMany(mappedBy = "tiposSensores")
    List<TipoSensor> tiposSensores;
}
