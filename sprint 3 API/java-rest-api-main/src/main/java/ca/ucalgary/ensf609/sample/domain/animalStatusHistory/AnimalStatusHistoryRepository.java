package ca.ucalgary.ensf609.sample.domain.animalStatusHistory;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface AnimalStatusHistoryRepository {

    String create(NewAnimalStatusHistory animalStatusHistory);
    List<AnimalStatusHistory> getAnimalStatusHistory();

}
