import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private Grammar grammar;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
    }

    private List<LRItem> closure(List<LRItem> items) {
        List<LRItem> cl = new ArrayList<>(items);
        boolean changed;

        do {
            changed = false;
            List<LRItem> clCopy = new ArrayList<>(cl);

            for (LRItem element : clCopy) {
                int dot = element.getDotIndex();
                if (element.getProduction().size() > dot) {
                    String symbol = element.getProduction().get(dot);
                    if (grammar.getNonTerminals().contains(symbol)) {
                        for (List<String> production : grammar.getProductionsForNonTerminal(symbol)) {
                            LRItem newItem = new LRItem(symbol, production);
                            if (!clCopy.contains(newItem)) {
                                cl.add(newItem);
                                changed = true;
                            }
                        }
                    }
                }
            }
        } while (changed);

        return cl;
    }

    private List<LRItem> goTo(List<LRItem> state, String symbol) {
        List<LRItem> items = state.stream()
                .filter(elem -> elem.getProduction().size() > elem.getDotIndex() && elem.getProduction().get(elem.getDotIndex()).equals(symbol))
                .collect(Collectors.toList());

        items = items.stream()
                .map(lrItem -> new LRItem(lrItem.getNonTerminal(), lrItem.getProduction(), lrItem.getDotIndex() + 1))
                .collect(Collectors.toList());

        return closure(items);
    }

    public List<List<LRItem>> canonicalCollection() {
        List<List<LRItem>> collection = new ArrayList<>();
        List<List<LRItem>> collectionCopy;

        List<LRItem> startingList = new ArrayList<>();
        startingList.add(new LRItem("S'", new ArrayList<>(Collections.singletonList(grammar.getStartingSymbol()))));
        List<LRItem> s0 = closure(startingList);
        collection.add(s0);

        System.out.println("s0 = " + s0 + "\n");

        boolean changed;
        do {
            changed = false;
            collectionCopy = new ArrayList<>(collection);
            for (List<LRItem> state : collectionCopy) {
                List<String> allSymbols = Stream.concat(grammar.getNonTerminals().stream(), grammar.getTerminals().stream()).collect(Collectors.toList());
                for (String symbol : allSymbols) {
                    List<LRItem> goToResult = goTo(state, symbol);
                    if (!goToResult.isEmpty() && !collection.contains(goToResult)) {
                        System.out.println("goTo(" + collection.indexOf(state) + ", " + symbol + ") = " + goToResult);
                        collection.add(goToResult);
                        changed = true;
                    }
                }
            }
        } while (changed);

        return collection;
    }
}
