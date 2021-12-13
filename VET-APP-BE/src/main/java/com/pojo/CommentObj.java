package com.pojo;

import com.enums.RequestStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@Entity
public class CommentObj {

    @NotBlank
    private String id;

    private String description;
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
