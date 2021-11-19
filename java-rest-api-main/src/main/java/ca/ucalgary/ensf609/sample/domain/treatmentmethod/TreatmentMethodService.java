package ca.ucalgary.ensf609.sample.domain.treatmentmethod;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class TreatmentMethodService {

    private final TreatmentMethodRepository treatmentMethodRepository;

    public String create(NewTreatmentMethod treatmentMethod) {
        return treatmentMethodRepository.create(treatmentMethod);
    }

    public List<TreatmentMethod> getTreatmentMethods(){return  treatmentMethodRepository.getTreatmentMethods();}

    public void deleteTreatmentMethod(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"TreatmentMethod id is required");
        treatmentMethodRepository.deleteTreatmentMethod(id);
    }

    public TreatmentMethod updateTreatmentMethod(TreatmentMethod treatmentMethod) throws UserNotFoundException{
        Objects.requireNonNull(treatmentMethod.getId(),"TreatmentMethod id is required for update");
        return  treatmentMethodRepository.updateTreatmentMethod(treatmentMethod);

    }



}
