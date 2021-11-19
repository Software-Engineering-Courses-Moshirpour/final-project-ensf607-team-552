package ca.ucalgary.ensf609.sample.domain.prescriptionitem;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class PrescriptionItem {

    String id;
    String type;
    String method;
    String name;
    String prescription;

}
