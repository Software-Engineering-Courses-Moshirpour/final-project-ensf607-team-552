package com.model;


import com.enums.AnimalStatus;
import com.enums.TMType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "treatmentmethod"
)
public class TreatmentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private TMType type;
}
