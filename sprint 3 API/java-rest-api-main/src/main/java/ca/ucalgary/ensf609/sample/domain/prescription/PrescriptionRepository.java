package ca.ucalgary.ensf609.sample.domain.prescription;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface PrescriptionRepository {

    String create(NewPrescription prescription);
    List<Prescription> getPrescriptions();
    void deletePrescription(String id) throws UserNotFoundException;
    Prescription updatePrescription(Prescription prescription) throws UserNotFoundException;
}
