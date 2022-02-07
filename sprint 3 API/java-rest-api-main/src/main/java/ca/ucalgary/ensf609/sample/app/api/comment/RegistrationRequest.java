package ca.ucalgary.ensf609.sample.app.api.comment;

import lombok.Value;

@Value
class RegistrationRequest {
    int userId;
    int animalId;
    String description;
}
