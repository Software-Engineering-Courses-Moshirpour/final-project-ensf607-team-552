package ca.ucalgary.ensf609.rest.client;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewPrescriptionItem {

    String type;
    String method;
    String name;
    String prescription;


}
