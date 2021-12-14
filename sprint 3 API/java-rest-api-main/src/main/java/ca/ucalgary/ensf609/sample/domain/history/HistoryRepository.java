package ca.ucalgary.ensf609.sample.domain.history;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface HistoryRepository {

    String create(NewHistory history);
    List<History> getHistory();
//    void deleteUser(String id) throws UserNotFoundException;
//    User updateUser(User user) throws UserNotFoundException;
}
