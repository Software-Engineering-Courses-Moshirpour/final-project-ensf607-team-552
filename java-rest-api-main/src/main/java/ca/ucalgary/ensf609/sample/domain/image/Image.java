package ca.ucalgary.ensf609.sample.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@Value
@Getter
@Builder
public class Image {

    String id;
    int userId;
    String createDate;
    String address;
    int animalID;
    String type;

}
