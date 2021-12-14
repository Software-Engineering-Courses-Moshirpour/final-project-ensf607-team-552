package ca.ucalgary.ensf609.sample.domain.animalStatusHistory;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class NewAnimalStatusHistory {
    String id;
    String dateAndTime;
    String description;
    String location;
    String status;
    int animal;
}
