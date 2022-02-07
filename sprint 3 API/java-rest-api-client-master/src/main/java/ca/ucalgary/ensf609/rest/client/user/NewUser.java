package ca.ucalgary.ensf609.rest.client.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewUser {

    String login;
    String password;
}
