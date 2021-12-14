package ca.ucalgary.ensf609.sample.domain.animalStatusHistory;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;

@Value
@Getter
@Builder
public class AnimalStatusHistory {

    String id;
    String dateAndTime;
    String description;
    String location;
    String status;
    int animal;

}
