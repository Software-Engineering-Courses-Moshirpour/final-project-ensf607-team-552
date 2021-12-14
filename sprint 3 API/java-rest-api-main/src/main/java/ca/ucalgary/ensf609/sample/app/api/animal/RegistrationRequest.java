package ca.ucalgary.ensf609.sample.app.api.animal;

import lombok.Value;

@Value
class RegistrationRequest {

    String type;
    String breed;
    String color;
}
