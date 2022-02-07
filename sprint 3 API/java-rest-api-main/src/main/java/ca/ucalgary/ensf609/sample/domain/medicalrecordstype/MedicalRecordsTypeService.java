package ca.ucalgary.ensf609.sample.domain.medicalrecordstype;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class MedicalRecordsTypeService {

    private final MedicalRecordsTypeRepository medicalRecordsTypeRepository;

    public String create(NewMedicalRecordsType medicalRecordsType) {
        return medicalRecordsTypeRepository.create(medicalRecordsType);
    }

    public List<MedicalRecordsType> getMedicalRecordsTypes(){return  medicalRecordsTypeRepository.getMedicalRecordsTypes();}

    public void deleteMedicalRecordsType(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"MedicalRecordsType id is required");
        medicalRecordsTypeRepository.deleteMedicalRecordsType(id);
    }

    public MedicalRecordsType updateMedicalRecordsType(MedicalRecordsType medicalRecordsType) throws UserNotFoundException{
        Objects.requireNonNull(medicalRecordsType.getId(),"MedicalRecordsType id is required for update");
        return  medicalRecordsTypeRepository.updateMedicalRecordsType(medicalRecordsType);

    }



}
