package com.pojo;

import com.enums.Status;
import com.model.Role;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;


@Data
@Entity
public class Userobj {

    @NotBlank
    private String id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<Role> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private LocalDate dob;

    private String phone;

    private Status status;

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
