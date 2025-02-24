package com.unam.agrosense.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tipos_actuadores")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoActuador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "actuadores_x_tipos_actuadores",
            joinColumns = @JoinColumn(name = "tipo_actuador_id"),
            inverseJoinColumns = @JoinColumn(name = "actuador_id")
    )
    private List<Actuador> actuadores;

    private String tipo; //On-off, MultiNivel, etc

}
