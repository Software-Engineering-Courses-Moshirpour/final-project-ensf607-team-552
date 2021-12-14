package ca.ucalgary.ensf609.sample.app.api.history;

import lombok.Value;

@Value
class RegistrationRequest {

    String date;
    String type;
    String value;
    int user;
    int animal;
}
