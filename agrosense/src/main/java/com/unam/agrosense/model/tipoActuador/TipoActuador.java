package com.unam.agrosense.model.tipoActuador;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unam.agrosense.model.actuador.Actuador;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tipos_actuadores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoActuador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    // Este atributo se refiere al comportamiento que tiene el actuador On-off, MultiNivel, etc
    @Enumerated(EnumType.STRING)
    private Comportamiento comportamiento;

    private boolean activo  = true;

    @ManyToMany
    @JoinTable(
            name = "actuadores_x_tipos_actuadores",
            joinColumns = @JoinColumn(name = "tipo_actuador_id"),
            inverseJoinColumns = @JoinColumn(name = "actuador_id")
    )
    @JsonIgnore
    private List<Actuador> actuadores;



}
