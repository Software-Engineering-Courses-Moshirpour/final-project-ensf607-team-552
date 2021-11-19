package ca.ucalgary.ensf609.sample.domain.prescription;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class NewPrescription {
    String id;
    int userId;
    int animalID;
    String createDate;
    String description ;

}
