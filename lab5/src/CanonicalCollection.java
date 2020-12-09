import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanonicalCollection {
    private List<List<LRItem>> collection;
    private Map<Pair<Integer, String>, Integer> goToHistory;

    public CanonicalCollection() {
        this.collection = new ArrayList<>();
        this.goToHistory = new HashMap<>();
    }

    public void addToCollection(List<LRItem> state) {
        collection.add(state);
    }

    public void addToGoToHistory(Pair<Integer, String> fromState, Integer toState) {
        goToHistory.putIfAbsent(fromState, toState);
    }

    public List<List<LRItem>> getCollection() {
        return collection;
    }

    public Map<Pair<Integer, String>, Integer> getGoToHistory() {
        return goToHistory;
    }
}
