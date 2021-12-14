package ca.ucalgary.ensf609.sample.app.api.animalStatusHistory;

import lombok.Value;

@Value
class RegistrationRequest {

    String dateAndTime;
    String description;
    String location;
    String status;
    int animal;
}
