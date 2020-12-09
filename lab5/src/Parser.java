import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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

    public CanonicalCollection canonicalCollection() {
        CanonicalCollection collection = new CanonicalCollection();
        List<List<LRItem>> collectionCopy;

        List<LRItem> startingList = new ArrayList<>();
        startingList.add(new LRItem("S'", new ArrayList<>(Collections.singletonList(grammar.getStartingSymbol()))));
        List<LRItem> s0 = closure(startingList);
        collection.addToCollection(s0);

        System.out.println("s0 = " + s0 + "\n");

        boolean changed;
        int stateIndex = 1;
        do {
            changed = false;
            collectionCopy = new ArrayList<>(collection.getCollection());

            for (List<LRItem> state : collectionCopy) {
                List<String> allSymbols = grammar.getAllSymbols();

                for (String symbol : allSymbols) {
                    List<LRItem> goToResult = goTo(state, symbol);

                    if (!goToResult.isEmpty()) {
                        int collectionIndex = collection.getCollection().indexOf(goToResult);

                        collection.addToGoToHistory(new Pair<>(collection.getCollection().indexOf(state), symbol),
                                collectionIndex == -1 ? stateIndex : collectionIndex);

                        if (collectionIndex == -1) {
                            System.out.println("s" + stateIndex + " = goTo(" + collection.getCollection().indexOf(state) + ", " + symbol + ") = " + goToResult);
                            collection.addToCollection(goToResult);
                            changed = true;
                            stateIndex += 1;
                        }
                    }
                }
            }
        } while (changed);

        return collection;
    }

    public Stack<Integer> parseSequence(Grammar grammar, LR0Table lr0Table, List<String> sequence) {
        int state = 0;

        Stack<String> alpha = new Stack<>();
        alpha.push("0");

        Stack<String> beta = new Stack<>();
        for (int i = sequence.size() - 1; i >= 0; i--) {
            beta.push(String.valueOf(sequence.get(i)));
        }

        Stack<Integer> output = new Stack<>();

        boolean end = false;
        do {
            if (lr0Table.getAction(state).equals("SHIFT")) {
                String a = beta.pop();

                state = lr0Table.goTo(state, a);

                alpha.push(a);
                alpha.push(String.valueOf(state));
            } else if (lr0Table.getAction(state).startsWith("REDUCE")) {
                // TODO: Is this ok?
                int l = Integer.parseInt(lr0Table.getAction(state).split(" ")[1]);

                Pair<String, List<String>> production = grammar.getOrderedProductions().get(l - 1);

                List<String> rhp = new ArrayList<>();
                while (!production.getValue().equals(rhp) && !alpha.empty()) {
                    String currentState = alpha.pop(); // what do we do with this?
                    if (!alpha.empty()) {
                        rhp.add(0, alpha.pop());
                    }
                }

                if (alpha.empty()) {
                    throw new RuntimeException("\nStack empty, sequence not found..");
                }

                state = lr0Table.goTo(Integer.parseInt(alpha.peek()), production.getKey());

                alpha.push(production.getKey());
                alpha.push(String.valueOf(state));

                output.push(l);
            } else if (lr0Table.getAction(state).equals("ACC")) {
                end = true;
                System.out.println("\nParsing successful..");
            } else if (lr0Table.getAction(state).equals("ERROR")) {
                throw new RuntimeException("\nError while parsing..");
            }
        } while (!end);

        return output;
    }
}
