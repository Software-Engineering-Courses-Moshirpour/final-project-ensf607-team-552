package ca.ucalgary.ensf609.sample.app.api.animalStatus;

import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatus;
import ca.ucalgary.ensf609.sample.domain.user.User;
import lombok.Value;

import java.util.List;

@Value
public class AnimalStatusListResponse {
    List<AnimalStatus> animalStatus;
}
