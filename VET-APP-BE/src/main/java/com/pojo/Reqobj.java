package com.pojo;

        import com.enums.RequestStatus;
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
public class Reqobj {

    @NotBlank
    private String id;

    private RequestStatus adminstatus;
    private RequestStatus techstatus;
    private LocalDate reqDate;
    private LocalDate returnDate;
    private String returnedUser;
    private long userid;
    private int animalid;
    private int instructId;


    public void setId(String id) {
        this.id = id;
    }
    @Id
    public String getId() {
        return id;
    }
}
