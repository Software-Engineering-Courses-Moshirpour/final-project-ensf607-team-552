package ca.ucalgary.ensf609.sample.domain.prescriptionitem;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface PrescriptionItemRepository {

    String create(NewPrescriptionItem prescription);
    List<PrescriptionItem> getPrescriptionItems();
    void deletePrescriptionItem(String id) throws UserNotFoundException;
    PrescriptionItem updatePrescriptionItem(PrescriptionItem prescription) throws UserNotFoundException;
}
