package ca.ucalgary.ensf609.sample.domain.history;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewHistory{
    int id;
    String date;
    String type;
    String value;
    int user;
    int animal;
}
