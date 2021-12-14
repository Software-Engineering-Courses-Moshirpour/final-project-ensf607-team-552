package com.model;


import com.enums.TreatmentRequestStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "treatmentRequest"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TreatmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;


    @Enumerated(EnumType.STRING)
    @Column(name = "techstatus", length = 20)
    private TreatmentRequestStatus techstatus;

    @Column(
            name = "req_date",
            nullable = false
    )
    private LocalDate reqDate;


    @Column(
            name = "careAttn_id",
            nullable = false
    )
    private int careAttnId;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_treatment_request_fk"
            )
    )
    private User user;


    @ManyToOne
    @JoinColumn(
            name = "animal_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "animal_treatment_request_fk"
            )
    )
    private Animal animal;

    @ManyToOne
    @JoinColumn(
            name = "prescribe_id",
            nullable = true,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "prescription_treatmentReq_fk"
            )
    )
    private Prescription prescription;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @JsonIgnore
    public User getUser() {
        return user;
    }
    @JsonIgnore
    public Animal getAnimal() {
        return animal;
    }
    @JsonIgnore
    public Prescription getPrescription() {
        return prescription;
    }
}
