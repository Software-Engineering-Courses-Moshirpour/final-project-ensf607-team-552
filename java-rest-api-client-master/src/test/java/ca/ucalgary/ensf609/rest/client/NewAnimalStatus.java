package ca.ucalgary.ensf609.rest.client;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewAnimalStatus {
    int animalId;
    String status;

}