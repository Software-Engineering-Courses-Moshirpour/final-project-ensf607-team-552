package com.model;


import com.enums.MedType;
import com.enums.Sex;
import com.enums.TMType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "prescribe"
)
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;

    @Column(
            name = "prescription ",
            nullable = false
    )
    private String prescription;


    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDate created;

    @Column(name="type",nullable = false)
    @Enumerated(EnumType.STRING)
    private TMType type;

    @ManyToOne
    @JoinColumn(
            name = "animalId",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "animal_prescribe_fk"
            )
    )
    private Animal animal;


    @ManyToOne
    @JoinColumn(
            name = "techUser",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_prescribe_fk"
            )
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "treatmentReq_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "treatmentReq_prescribe_fk"
            )
    )
    private TreatmentRequest treatmentRequest;

    @Column(
            name = "careAttnUser",
            nullable = false
    )
    private int careAttnUser;

    @JsonIgnore
    public Animal getAnimal() {
        return animal;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
