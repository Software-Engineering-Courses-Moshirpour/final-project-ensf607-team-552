package ca.ucalgary.ensf609.sample.app.api.treatmentmethod;

//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethod;
import lombok.Value;

import java.util.List;

@Value
public class TreatmentMethodListResponse {
    List<TreatmentMethod> treatmentMethod;
}
