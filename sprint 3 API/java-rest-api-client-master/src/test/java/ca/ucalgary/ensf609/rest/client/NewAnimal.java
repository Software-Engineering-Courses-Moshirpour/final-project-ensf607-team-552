package ca.ucalgary.ensf609.rest.client;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewAnimal {
    String type;
    String breed;
    String color;
}
