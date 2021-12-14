package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.NewPrescriptionItem;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItem;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItemRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPrescriptionItemRepository implements PrescriptionItemRepository {

    private static final Map<String, PrescriptionItem> PRESCRIPTIONITEMS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewPrescriptionItem newPrescriptionItem) {
        String id = UUID.randomUUID().toString();
        PrescriptionItem prescriptionItem = PrescriptionItem.builder()
                .id(id)
                .type(newPrescriptionItem.getType())
                .method(newPrescriptionItem.getMethod())
                .name(newPrescriptionItem.getName())
                .prescription(newPrescriptionItem.getPrescription())
                .build();
        PRESCRIPTIONITEMS_STORE.put(id, prescriptionItem);

        return id;
    }
    @Override
    public List<PrescriptionItem> getPrescriptionItems(){
        return new ArrayList<>(PRESCRIPTIONITEMS_STORE.values());
    }


    @Override
    public  void deletePrescriptionItem(String id) throws UserNotFoundException {
        PrescriptionItem prescriptionItem= Optional.of(PRESCRIPTIONITEMS_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "prescriptionItem not found."));
        PRESCRIPTIONITEMS_STORE.remove(prescriptionItem.getId(),prescriptionItem);
    }
    @Override
    public  PrescriptionItem updatePrescriptionItem(PrescriptionItem prescriptionItem){
        Optional.of(PRESCRIPTIONITEMS_STORE.get(prescriptionItem.getId())).orElseThrow(()->  new UserNotFoundException(404, "prescriptionItem not found."));
        PRESCRIPTIONITEMS_STORE.replace(prescriptionItem.getId(), prescriptionItem);
        return  prescriptionItem;

    }


}
