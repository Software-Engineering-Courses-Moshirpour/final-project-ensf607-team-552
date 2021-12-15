package com.pojo;

import lombok.Data;

import com.enums.TMType;
import com.model.TreatmentMethod;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@Entity
public class PrescriptionObj {

    @NotBlank
    private int id;

    @NotBlank
    private TMType type;
    @NotBlank
    private long userId;
    @NotBlank
    private int animalId;
    @NotBlank
    private int careAttnId;
    @NotBlank
    private LocalDate createdAt;
    @NotBlank
    private int treatmentReqId;
    @NotBlank
    private String prescription;

    public void setId(int id) {
        this.id = id;
    }
    @Id
    public int getId() {
        return id;
    }
}
