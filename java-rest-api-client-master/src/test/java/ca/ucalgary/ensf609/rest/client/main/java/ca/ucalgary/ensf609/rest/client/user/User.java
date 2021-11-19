package ca.ucalgary.ensf609.rest.client.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
@AllArgsConstructor
public class User {

    String id;
    String login;
    String password;
}
