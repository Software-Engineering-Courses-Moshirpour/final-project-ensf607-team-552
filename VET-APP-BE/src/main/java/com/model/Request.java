package com.model;


import com.enums.AnimalStatus;
import com.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "request"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(name = "adminstatus", length = 20)
    private RequestStatus adminstatus;


    @Enumerated(EnumType.STRING)
    @Column(name = "techstatus", length = 20)
    private RequestStatus techstatus;

    @Column(
            name = "req_date",
            nullable = false
    )
    private LocalDate reqDate;

    @Column(
            name = "return_date",
            nullable = false
    )
    private LocalDate returnDate;


    @Column(
            name = "return_by",
            nullable = false
    )
    private String returnedUser;

    @Column(
            name = "instruct_id",
            nullable = false
    )
    private int instructId;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_request_fk"
            )
    )
    private User user;


    @ManyToOne
    @JoinColumn(
            name = "animal_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "animal_request_fk"
            )
    )
    private Animal animal;

    @JsonIgnore
    public User getUser() {
        return user;
    }
    @JsonIgnore
    public Animal getAnimal() {
        return animal;
    }
}
