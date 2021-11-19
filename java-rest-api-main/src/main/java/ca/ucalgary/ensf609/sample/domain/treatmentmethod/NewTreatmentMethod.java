package ca.ucalgary.ensf609.sample.domain.treatmentmethod;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class NewTreatmentMethod {
    String id;
    String type;
}
