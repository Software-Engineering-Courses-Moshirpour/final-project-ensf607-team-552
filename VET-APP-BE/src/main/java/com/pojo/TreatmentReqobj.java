package com.pojo;

import com.enums.RequestStatus;
import com.enums.TreatmentRequestStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@Entity
public class TreatmentReqobj {

    @NotBlank
    private int id;
    private TreatmentRequestStatus techStatus;
    private LocalDate reqDate;
    private long userId;
    private int animalId;
    private int careAttnId;
    private String description;


    public void setId(int id) {
        this.id = id;
    }
    @Id
    public int getId() {
        return id;
    }

    public long getTechId() {
        return userId;
    }
    public void setTechId(long userId) {
        this.userId = userId;
    }
    public int getAnimalId() {
        return animalId;
    }
    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }
    public int getCareAttnId() {
        return careAttnId;
    }
    public void setCareAttnId(int careAttnId) {
        this.careAttnId = careAttnId;
    }
}
