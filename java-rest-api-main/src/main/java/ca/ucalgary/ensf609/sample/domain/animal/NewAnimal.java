package ca.ucalgary.ensf609.sample.domain.animal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewAnimal {
    String id;
    String type;
    String breed;
    String color;
}
