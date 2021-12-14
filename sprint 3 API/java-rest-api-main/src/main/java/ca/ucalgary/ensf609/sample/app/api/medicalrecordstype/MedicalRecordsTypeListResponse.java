package ca.ucalgary.ensf609.sample.app.api.medicalrecordstype;

//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsType;
import lombok.Value;

import java.util.List;

@Value
public class MedicalRecordsTypeListResponse {
    List<MedicalRecordsType> medicalRecordsType;
}
