package com.pojo;

import lombok.Data;

import com.enums.TMType;
import com.model.TreatmentMethod;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@Data
@Entity
public class PrescriptionObj {

    @NotBlank
    private String id;

    @NotBlank
    private TMType type;
    @NotBlank
    private long userId;
    @NotBlank
    private int animalId;
    @NotBlank
    private int careAttnId;
    @NotBlank
    private int treatmentReqId;
    @NotBlank
    private String prescription;

    public void setId(String id) {
        this.id = id;
    }
    @Id
    public String getId() {
        return id;
    }
}
