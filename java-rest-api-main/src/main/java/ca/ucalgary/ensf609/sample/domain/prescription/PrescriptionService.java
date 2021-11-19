package ca.ucalgary.ensf609.sample.domain.prescription;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public String create(NewPrescription prescription) {
        return prescriptionRepository.create(prescription);
    }

    public List<Prescription> getPrescriptions(){return  prescriptionRepository.getPrescriptions();}

    public void deletePrescription(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"Prescription id is required");
        prescriptionRepository.deletePrescription(id);
    }

    public Prescription updatePrescription(Prescription prescription) throws UserNotFoundException{
        Objects.requireNonNull(prescription.getId(),"Prescription id is required for update");
        return  prescriptionRepository.updatePrescription(prescription);

    }



}
