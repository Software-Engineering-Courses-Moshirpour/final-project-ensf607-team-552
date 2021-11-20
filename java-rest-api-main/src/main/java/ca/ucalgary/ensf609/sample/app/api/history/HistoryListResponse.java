package ca.ucalgary.ensf609.sample.app.api.history;

import ca.ucalgary.ensf609.sample.domain.history.History;
import lombok.Value;

import java.util.List;

@Value
public class HistoryListResponse {
    List<History> History;
}
