package ca.ucalgary.ensf609.sample.domain.animalStatus;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class AnimalStatusService {

    private final AnimalStatusRepository animalStatusRepository;

    public String create(NewAnimalStatus animalStatus) {
        return animalStatusRepository.create(animalStatus);
    }

    public List<AnimalStatus> getAnimalStatus(){return  animalStatusRepository.getAnimalStatus();}




}
