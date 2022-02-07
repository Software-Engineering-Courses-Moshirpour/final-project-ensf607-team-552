package com.pojo;

import com.enums.DailyStatusTypes;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@Entity
public class AnimalStatusHistoryObj {

    @NotBlank
    private String id;
    @NotBlank
    private LocalDate createdAt;
    @NotBlank
    private String description;

    private String location;
    @NotBlank
    private DailyStatusTypes status;
    @NotBlank
    private long userId;
    @NotBlank
    private int animalId;


    public void setId(String id) {
        this.id = id;
    }
    @Id
    public String getId() {
        return id;
    }
}