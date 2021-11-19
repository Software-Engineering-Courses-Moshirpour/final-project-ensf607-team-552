package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatus;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatusRepository;
import ca.ucalgary.ensf609.sample.domain.animalStatus.NewAnimalStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAnimalStatusRepository implements AnimalStatusRepository {

    private static final Map<String, AnimalStatus> ANIMALSTATUS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewAnimalStatus newAnimalStatus) {
        String id = UUID.randomUUID().toString();
        AnimalStatus animalStatus = AnimalStatus.builder()
                .id(id)
                .animalId(newAnimalStatus.getAnimalId())
                .status(newAnimalStatus.getStatus())
                .build();
        ANIMALSTATUS_STORE.put(id, animalStatus);

        return id;
    }

    @Override
    public List<AnimalStatus> getAnimalStatus() {
        return new ArrayList<>(ANIMALSTATUS_STORE.values());
    }
}
