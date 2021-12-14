package ca.ucalgary.ensf609.sample.domain.animalStatusHistory;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class AnimalStatusHistoryService {

    private final AnimalStatusHistoryRepository animalStatusHistoryRepository;

    public String create(NewAnimalStatusHistory animalStatusHistory) {
        return animalStatusHistoryRepository.create(animalStatusHistory);
    }

    public List<AnimalStatusHistory> getAnimalStatusHistory(){return  animalStatusHistoryRepository.getAnimalStatusHistory();}




}
