package ca.ucalgary.ensf609.rest.client;

import lombok.Builder;
import lombok.Value;
import java.util.Date;
@Value
@Builder
public class NewImage {

    int userId;
    String createDate;
    String address;
    int animalID;
    String type;

}
