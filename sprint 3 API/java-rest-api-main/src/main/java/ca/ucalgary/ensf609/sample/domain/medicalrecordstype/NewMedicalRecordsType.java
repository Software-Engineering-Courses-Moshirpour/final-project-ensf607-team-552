package ca.ucalgary.ensf609.sample.domain.medicalrecordstype;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class NewMedicalRecordsType {
    String id;
    String type;
}
