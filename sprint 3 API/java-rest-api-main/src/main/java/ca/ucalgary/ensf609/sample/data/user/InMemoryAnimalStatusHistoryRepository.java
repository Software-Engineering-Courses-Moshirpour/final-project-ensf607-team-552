package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistory;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistoryRepository;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.NewAnimalStatusHistory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAnimalStatusHistoryRepository implements AnimalStatusHistoryRepository {

    private static final Map<String, AnimalStatusHistory> ANIMALSTATUSHISTORY_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewAnimalStatusHistory newAnimalStatusHistory) {
        String id = UUID.randomUUID().toString();
        AnimalStatusHistory animalStatusHistory = AnimalStatusHistory.builder()
                .id(id)
                .dateAndTime(newAnimalStatusHistory.getDateAndTime())
                .description(newAnimalStatusHistory.getDescription())
                .location(newAnimalStatusHistory.getLocation())
                .status(newAnimalStatusHistory.getStatus())
                .animal(newAnimalStatusHistory.getAnimal())
                .build();
        ANIMALSTATUSHISTORY_STORE.put(id, animalStatusHistory);

        return id;
    }
    @Override
    public List<AnimalStatusHistory> getAnimalStatusHistory(){
        return new ArrayList<>(ANIMALSTATUSHISTORY_STORE.values());
    }
//    @Override
//    public  void deleteAnimal(String id) throws UserNotFoundException {
//        Animal animal= Optional.of(ANIMALS_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "Animal not found."));
//        ANIMALS_STORE.remove(animal.getId(),animal);
//    }
//    @Override
//    public  Animal updateAnimal(Animal animal){
//        Optional.of(ANIMALS_STORE.get(animal.getId())).orElseThrow(()->  new UserNotFoundException(404, "Animal not found."));
//        ANIMALS_STORE.replace(animal.getId(), animal);
//        return  animal;
//
//    }


}
