package ca.ucalgary.ensf609.sample.domain.comment;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewComment {
    String id;
    int userId;
    int animalId;
    String description;
}
