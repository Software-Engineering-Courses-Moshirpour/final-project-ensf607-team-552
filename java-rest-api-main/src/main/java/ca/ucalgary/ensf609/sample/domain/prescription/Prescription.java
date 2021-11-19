package ca.ucalgary.ensf609.sample.domain.prescription;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Value
@Getter
@Builder
public class Prescription {

    String id;
    int userId;
    int animalID;
    String createDate;
    String description;

}
