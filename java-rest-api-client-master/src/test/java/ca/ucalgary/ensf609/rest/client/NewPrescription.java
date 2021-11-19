package ca.ucalgary.ensf609.rest.client;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
@Value
@Builder
public class NewPrescription {

    int userId;
    int animalID;
    String createDate;
    String description;

}
