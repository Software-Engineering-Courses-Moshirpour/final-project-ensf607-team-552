package ca.ucalgary.ensf609.sample.domain.animal;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface AnimalRepository {

    String create(NewAnimal animal);
    List<Animal> getAnimals();
    void deleteAnimal(String id) throws UserNotFoundException;
    Animal updateAnimal(Animal animal) throws UserNotFoundException;
}
