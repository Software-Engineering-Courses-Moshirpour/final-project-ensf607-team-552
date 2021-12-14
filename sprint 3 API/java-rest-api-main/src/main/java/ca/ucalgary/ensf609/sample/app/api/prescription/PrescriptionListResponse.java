package ca.ucalgary.ensf609.sample.app.api.prescription;

//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import ca.ucalgary.ensf609.sample.domain.prescription.Prescription;
import lombok.Value;

import java.util.List;

@Value
public class PrescriptionListResponse {
    List<Prescription> prescriptions;
}
