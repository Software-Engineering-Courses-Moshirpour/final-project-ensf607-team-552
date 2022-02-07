package ca.ucalgary.ensf609.sample.domain.history;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class History {
    String id;
    String date;
    String type;
    String value;
    int user;
    int animal;
}
