package ca.ucalgary.ensf609.sample.domain.medicalrecordstype;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Value
@Getter
@Builder
public class MedicalRecordsType {

    String id;
    String type;

}
