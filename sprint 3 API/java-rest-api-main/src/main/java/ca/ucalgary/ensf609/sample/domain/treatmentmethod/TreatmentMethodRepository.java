package ca.ucalgary.ensf609.sample.domain.treatmentmethod;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface TreatmentMethodRepository {

    String create(NewTreatmentMethod treatmentMethod);
    List<TreatmentMethod> getTreatmentMethods();
    void deleteTreatmentMethod(String id) throws UserNotFoundException;
    TreatmentMethod updateTreatmentMethod(TreatmentMethod treatmentMethod) throws UserNotFoundException;
}
