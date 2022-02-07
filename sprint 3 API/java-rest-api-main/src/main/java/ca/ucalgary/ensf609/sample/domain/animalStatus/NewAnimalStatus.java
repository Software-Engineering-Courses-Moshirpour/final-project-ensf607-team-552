package ca.ucalgary.ensf609.sample.domain.animalStatus;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewAnimalStatus {
    int id;
    int animalId;
    String status;
}
