package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsType;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsTypeRepository;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.NewMedicalRecordsType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMedicalRecordsTypeRepository implements MedicalRecordsTypeRepository {

    private static final Map<String, MedicalRecordsType> MEDICALRECORDSTYPES_STORE = new ConcurrentHashMap();

    @Override
    public List<MedicalRecordsType> getMedicalRecordsTypes(){
        return new ArrayList<>(MEDICALRECORDSTYPES_STORE.values());
    }


    @Override
    public String create(NewMedicalRecordsType newMedicalRecordsType) {
        String id = UUID.randomUUID().toString();
        MedicalRecordsType medicalRecordsType = MedicalRecordsType.builder()
                .id(id)
                .type(newMedicalRecordsType.getType())
                .build();
        MEDICALRECORDSTYPES_STORE.put(id, medicalRecordsType);

        return id;
    }

    @Override
    public  void deleteMedicalRecordsType(String id) throws UserNotFoundException {
        MedicalRecordsType medicalRecordsType= Optional.of(MEDICALRECORDSTYPES_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "medicalRecordsType not found."));
        MEDICALRECORDSTYPES_STORE.remove(medicalRecordsType.getId(),medicalRecordsType);
    }
    @Override
    public  MedicalRecordsType updateMedicalRecordsType(MedicalRecordsType medicalRecordsType){
        Optional.of(MEDICALRECORDSTYPES_STORE.get(medicalRecordsType.getId())).orElseThrow(()->  new UserNotFoundException(404, "medicalRecordsType not found."));
        MEDICALRECORDSTYPES_STORE.replace(medicalRecordsType.getId(), medicalRecordsType);
        return  medicalRecordsType;

    }


}
