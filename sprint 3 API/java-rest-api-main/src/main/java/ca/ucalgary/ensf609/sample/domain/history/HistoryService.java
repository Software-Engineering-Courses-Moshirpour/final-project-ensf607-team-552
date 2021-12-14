package ca.ucalgary.ensf609.sample.domain.history;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    public String create(NewHistory history) {
        return historyRepository.create(history);
    }

    public List<History> getHistory(){return  historyRepository.getHistory();}






}
