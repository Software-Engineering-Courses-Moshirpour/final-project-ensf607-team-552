package ca.ucalgary.ensf609.sample.domain.treatmentmethod;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Value
@Getter
@Builder
public class TreatmentMethod {

    String id;
    String type;

}
