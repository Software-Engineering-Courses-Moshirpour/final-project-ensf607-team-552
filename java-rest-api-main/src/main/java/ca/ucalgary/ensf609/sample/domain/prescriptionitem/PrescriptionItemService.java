package ca.ucalgary.ensf609.sample.domain.prescriptionitem;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class PrescriptionItemService {

    private final PrescriptionItemRepository prescriptionItemRepository;

    public String create(NewPrescriptionItem prescriptionItem) {
        return prescriptionItemRepository.create(prescriptionItem);
    }

    public List<PrescriptionItem> getPrescriptionItems(){return  prescriptionItemRepository.getPrescriptionItems();}

    public void deletePrescriptionItem(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"PrescriptionItem id is required");
        prescriptionItemRepository.deletePrescriptionItem(id);
    }

    public PrescriptionItem updatePrescriptionItem(PrescriptionItem prescriptionItem) throws UserNotFoundException{
        Objects.requireNonNull(prescriptionItem.getId(),"PrescriptionItem id is required for update");
        return  prescriptionItemRepository.updatePrescriptionItem(prescriptionItem);

    }



}
