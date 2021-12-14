package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.prescription.Prescription;
import ca.ucalgary.ensf609.sample.domain.prescription.PrescriptionRepository;
import ca.ucalgary.ensf609.sample.domain.prescription.NewPrescription;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPrescriptionRepository implements PrescriptionRepository {

    private static final Map<String, Prescription> PRESCRIPTIONS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewPrescription newPrescription) {
        String id = UUID.randomUUID().toString();
        Prescription prescription = Prescription.builder()
                .id(id)
                .userId(newPrescription.getUserId())
                .animalID(newPrescription.getAnimalID())
                .createDate(newPrescription.getCreateDate())
                .description(newPrescription.getDescription())
                .build();
        PRESCRIPTIONS_STORE.put(id, prescription);

        return id;
    }
    @Override
    public List<Prescription> getPrescriptions(){
        return new ArrayList<>(PRESCRIPTIONS_STORE.values());
    }


    @Override
    public  void deletePrescription(String id) throws UserNotFoundException {
        Prescription prescription= Optional.of(PRESCRIPTIONS_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "prescription not found."));
        PRESCRIPTIONS_STORE.remove(prescription.getId(),prescription);
    }
    @Override
    public  Prescription updatePrescription(Prescription prescription){
        Optional.of(PRESCRIPTIONS_STORE.get(prescription.getId())).orElseThrow(()->  new UserNotFoundException(404, "prescription not found."));
        PRESCRIPTIONS_STORE.replace(prescription.getId(), prescription);
        return  prescription;

    }


}
