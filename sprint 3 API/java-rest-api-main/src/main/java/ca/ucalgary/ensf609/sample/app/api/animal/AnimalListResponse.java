package ca.ucalgary.ensf609.sample.app.api.animal;

import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import lombok.Value;

import java.util.List;

@Value
public class AnimalListResponse {
    List<Animal> animals;
}
