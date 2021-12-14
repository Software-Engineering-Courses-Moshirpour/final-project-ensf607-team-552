package ca.ucalgary.ensf609.sample.app.api.prescription;

import lombok.Value;
import java.util.Date;
@Value
class RegistrationRequest {
    int userId;
    int animalID;
    String createDate;
    String description;
}
