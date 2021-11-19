package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import ca.ucalgary.ensf609.sample.domain.animal.AnimalRepository;
import ca.ucalgary.ensf609.sample.domain.animal.NewAnimal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAnimalRepository implements AnimalRepository {

    private static final Map<String, Animal> ANIMALS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewAnimal newAnimal) {
        String id = UUID.randomUUID().toString();
        Animal animal = Animal.builder()
                .id(id)
                .breed(newAnimal.getBreed())
                .color(newAnimal.getColor())
                .type(newAnimal.getType())
                .build();
        ANIMALS_STORE.put(id, animal);

        return id;
    }
    @Override
    public List<Animal> getAnimals(){
        return new ArrayList<>(ANIMALS_STORE.values());
    }
    @Override
    public  void deleteAnimal(String id) throws UserNotFoundException {
        Animal animal= Optional.of(ANIMALS_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "Animal not found."));
        ANIMALS_STORE.remove(animal.getId(),animal);
    }
    @Override
    public  Animal updateAnimal(Animal animal){
        Optional.of(ANIMALS_STORE.get(animal.getId())).orElseThrow(()->  new UserNotFoundException(404, "Animal not found."));
        ANIMALS_STORE.replace(animal.getId(), animal);
        return  animal;

    }


}
