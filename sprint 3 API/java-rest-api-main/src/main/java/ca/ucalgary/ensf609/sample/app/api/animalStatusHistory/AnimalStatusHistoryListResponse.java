package ca.ucalgary.ensf609.sample.app.api.animalStatusHistory;

import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistory;
import ca.ucalgary.ensf609.sample.domain.user.User;
import lombok.Value;

import java.util.List;

@Value
public class AnimalStatusHistoryListResponse {
    List<AnimalStatusHistory> animalStatusHistory;
}
