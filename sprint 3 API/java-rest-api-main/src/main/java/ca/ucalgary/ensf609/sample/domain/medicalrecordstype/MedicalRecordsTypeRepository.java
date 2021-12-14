package ca.ucalgary.ensf609.sample.domain.medicalrecordstype;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface MedicalRecordsTypeRepository {

    String create(NewMedicalRecordsType medicalRecordsType);
    List<MedicalRecordsType> getMedicalRecordsTypes();
    void deleteMedicalRecordsType(String id) throws UserNotFoundException;
    MedicalRecordsType updateMedicalRecordsType(MedicalRecordsType medicalRecordsType) throws UserNotFoundException;
}
