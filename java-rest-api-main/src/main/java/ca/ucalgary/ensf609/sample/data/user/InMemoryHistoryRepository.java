package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.history.History;
import ca.ucalgary.ensf609.sample.domain.history.HistoryRepository;
import ca.ucalgary.ensf609.sample.domain.history.NewHistory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryHistoryRepository implements HistoryRepository {

    private static final Map<String, History> HISTORY_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewHistory newHistory) {
        String id = UUID.randomUUID().toString();
        History history = History.builder()
                .id(id)
                .date(newHistory.getDate())
                .type(newHistory.getType())
                .value(newHistory.getValue())
                .user(newHistory.getUser())
                .animal(newHistory.getAnimal())
                .build();
        HISTORY_STORE.put(id, history);

        return id;
    }
    @Override
    public List<History> getHistory(){
        return new ArrayList<>(HISTORY_STORE.values());
    }



}
