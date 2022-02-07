package ca.ucalgary.ensf609.sample.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Comment {

    String id;
    int userId;
    int animalId;
    String description;
}
