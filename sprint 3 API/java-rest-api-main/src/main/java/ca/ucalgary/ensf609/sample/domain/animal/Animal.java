package ca.ucalgary.ensf609.sample.domain.animal;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Animal {

    String id;
    String type;
    String breed;
    String color;
}
