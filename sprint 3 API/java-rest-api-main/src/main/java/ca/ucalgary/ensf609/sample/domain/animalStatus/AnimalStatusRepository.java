package ca.ucalgary.ensf609.sample.domain.animalStatus;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface AnimalStatusRepository {

    String create(NewAnimalStatus animalStatus);
    List<AnimalStatus> getAnimalStatus();
}
