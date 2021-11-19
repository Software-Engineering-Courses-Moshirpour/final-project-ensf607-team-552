package ca.ucalgary.ensf609.sample.domain.prescriptionitem;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewPrescriptionItem {
    String id;
    String type;
    String method;
    String name;
    String prescription;

}
