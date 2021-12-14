package ca.ucalgary.ensf609.sample.app.api.prescriptionitem;

import lombok.Value;

@Value
class RegistrationRequest {
    String type;
    String method;
    String name;
    String prescription;

}
