package com.model;


import com.enums.AnimalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "animalstatushist"
)
public class AnimalStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;

    @Column(
            name = "date",
            nullable = false
    )
    private LocalDate createdAt;

    @Column(name="description")
    private String description;


    @Column(name="location")
    private String location;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AnimalStatus status;

    @ManyToOne
    @JoinColumn(
            name = "animal",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "animal_hist_fk"
            )
    )
    private Animal animal;




}
