package com.model;


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
        name = "comment"
)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;


    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDate created;


    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;


    @ManyToOne
    @JoinColumn(
            name = "user",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_comment_fk"
            )
    )
    private User user;




    @ManyToOne
    @JoinColumn(
            name = "animal",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "animal_comment_fk"
            )
    )
    private Animal animal;


    @JsonIgnore
    public Animal getAnimal() {
        return animal;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }


}


