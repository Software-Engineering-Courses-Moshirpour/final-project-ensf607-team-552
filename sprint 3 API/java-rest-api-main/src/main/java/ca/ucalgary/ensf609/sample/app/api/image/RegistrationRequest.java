package ca.ucalgary.ensf609.sample.app.api.image;

import lombok.Value;
import java.util.Date;
@Value
class RegistrationRequest {
    int userId;
    String createDate;
    String address;
    int animalID;
    String type;

}
