package ca.ucalgary.ensf609.sample.domain.image;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class NewImage {
    String id;
    int userId;
    String createDate;
    String address;
    int animalID;
    String type;


}
