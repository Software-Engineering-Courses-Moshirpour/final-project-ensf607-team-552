package ca.ucalgary.ensf609.rest.client;

import lombok.Builder;
import lombok.Value;
@Value
@Builder

public class NewAnimalStatusHistory {
    String dateAndTime;
    String description;
    String location;
    String status;
    int animal;
}