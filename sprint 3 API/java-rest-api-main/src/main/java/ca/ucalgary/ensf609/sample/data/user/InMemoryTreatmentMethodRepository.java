package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethod;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethodRepository;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.NewTreatmentMethod;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTreatmentMethodRepository implements TreatmentMethodRepository {

    private static final Map<String, TreatmentMethod> TREATMENTMETHODS_STORE = new ConcurrentHashMap();

    @Override
    public List<TreatmentMethod> getTreatmentMethods(){
        return new ArrayList<>(TREATMENTMETHODS_STORE.values());
    }


    @Override
    public String create(NewTreatmentMethod newTreatmentMethod) {
        String id = UUID.randomUUID().toString();
        TreatmentMethod treatmentMethod = TreatmentMethod.builder()
                .id(id)
                .type(newTreatmentMethod.getType())
                .build();
        TREATMENTMETHODS_STORE.put(id, treatmentMethod);

        return id;
    }

    @Override
    public  void deleteTreatmentMethod(String id) throws UserNotFoundException {
        TreatmentMethod treatmentMethod= Optional.of(TREATMENTMETHODS_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "treatmentMethod not found."));
        TREATMENTMETHODS_STORE.remove(treatmentMethod.getId(),treatmentMethod);
    }
    @Override
    public  TreatmentMethod updateTreatmentMethod(TreatmentMethod treatmentMethod){
        Optional.of(TREATMENTMETHODS_STORE.get(treatmentMethod.getId())).orElseThrow(()->  new UserNotFoundException(404, "treatmentMethod not found."));
        TREATMENTMETHODS_STORE.replace(treatmentMethod.getId(), treatmentMethod);
        return  treatmentMethod;

    }


}
