package ca.ucalgary.ensf609.sample.app.api.animalStatus;

import lombok.Value;

@Value
class RegistrationRequest {

    int animalId;
    String status;
}
