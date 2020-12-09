import java.util.*;
import java.util.stream.IntStream;

public class LR0Table {
    private List<Pair<String, Map<String, Integer>>> table;
    private List<String> allSymbols;

    public LR0Table(CanonicalCollection canonicalCollection, List<Pair<String, List<String>>> orderedProductions, List<String> allSymbols) {
        this.table = canonicalCollectionToLR0Table(canonicalCollection, orderedProductions);
        this.allSymbols = allSymbols;
    }

    private static List<Pair<String, Map<String, Integer>>> canonicalCollectionToLR0Table(
            CanonicalCollection canonicalCollection, List<Pair<String, List<String>>> orderedProductions) {
        List<Pair<String, Map<String, Integer>>> collection = new ArrayList<>();

        for (List<LRItem> state : canonicalCollection.getCollection()) {
            String action = "ERROR";
            Map<String, Integer> goToMap = new HashMap<>();

            Set<String> actionPerItem = new HashSet<>();
            for (LRItem element : state) {
                if (element.getNonTerminal().equals("S'") && element.getDotIndex() == element.getProduction().size()) {
                    actionPerItem.add("ACC");
                } else if (element.getDotIndex() == element.getProduction().size()) {
                    int productionIndex = IntStream.range(0, orderedProductions.size())
                            .filter(i -> {
                                Pair<String, List<String>> production = orderedProductions.get(i);
                                return production.getKey().equals(element.getNonTerminal()) && production.getValue().equals(element.getProduction());
                            }).findFirst().orElse(-1);
                    actionPerItem.add("REDUCE " + (productionIndex + 1));
                } else if (element.getDotIndex() >= 0 && element.getDotIndex() < element.getProduction().size()) {
                    actionPerItem.add("SHIFT");
                }
            }

            if (actionPerItem.size() == 1) {
                action = actionPerItem.toArray(new String[0])[0];
            }

            if (action.equals("SHIFT")) {
                for (Pair<Integer, String> fromState : canonicalCollection.getGoToHistory().keySet()) {
                    if (fromState.getKey().equals(canonicalCollection.getCollection().indexOf(state))) {
                        Integer toState = canonicalCollection.getGoToHistory().get(fromState);
                        goToMap.put(fromState.getValue(), toState);
                    }
                }
            }

            Pair<String, Map<String, Integer>> entry = new Pair<>(action, goToMap);

            collection.add(entry);
        }

        return collection;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Nr | Action   | goto\n");
        stringBuilder.append("   |          | ");

        for (String symbol : allSymbols) {
            stringBuilder.append(symbol).append(" | ");
        }
        stringBuilder.append("\n");

        for (Pair<String, Map<String, Integer>> entry : table) {
            stringBuilder.append(table.indexOf(entry)).append("  | ");
            stringBuilder.append(entry.getKey());
            for (int i = 0; i < 8 - entry.getKey().length(); i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" | ");

            for (String symbol : allSymbols) {
                Integer toState = entry.getValue().get(symbol);
                stringBuilder.append(toState != null ? toState : " ").append(" | ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public String getAction(int state) {
        return table.get(state).getKey();
    }

    public int goTo(int state, String a) {
        return table.get(state).getValue().get(a);
    }
}
