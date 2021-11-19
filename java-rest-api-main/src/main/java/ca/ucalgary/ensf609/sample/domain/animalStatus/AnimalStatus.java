package ca.ucalgary.ensf609.sample.domain.animalStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class AnimalStatus {
    String id;
    int animalId;
    String status;
}
