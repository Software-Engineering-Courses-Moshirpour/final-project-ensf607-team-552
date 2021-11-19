package ca.ucalgary.ensf609.sample.domain.animal;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public String create(NewAnimal animal) {
        return animalRepository.create(animal);
    }

    public List<Animal> getAnimals(){return  animalRepository.getAnimals();}

    public void deleteAnimal(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"Animal id is required");
        animalRepository.deleteAnimal(id);
    }

    public Animal updateUser(Animal animal) throws UserNotFoundException{
        Objects.requireNonNull(animal.getId(),"Animal id is required for update");
        return  animalRepository.updateAnimal(animal);

    }



}
