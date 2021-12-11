package com.model;


import com.enums.MedType;
import com.enums.Sex;
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
            name = "description ",
            nullable = false
    )
    private String description;


    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDate created;

    @Column(name="type",nullable = false)
    @Enumerated(EnumType.STRING)
    private MedType type;

    @ManyToOne
    @JoinColumn(
            name = "animal",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "animal_prescribe_fk"
            )
    )
    private Animal animal;


    @ManyToOne
    @JoinColumn(
            name = "user",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_prescribe_fk"
            )
    )
    private User user;

    @JsonIgnore
    public Animal getAnimal() {
    return animal;
}

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
