package ca.ucalgary.ensf609.rest.client;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewHistory {
    String date;
    String type;
    String value;
    int user;
    int animal;
}