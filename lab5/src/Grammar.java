import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grammar {
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private String startingSymbol;
    private Map<String, List<List<String>>> productions;
    private List<Pair<String, List<String>>> orderedProductions;

    private static boolean validateGrammar(Set<String> nonTerminals, Set<String> terminals,
                                           String startingSymbol, Map<String, List<List<String>>> productions) {
        if (!nonTerminals.contains(startingSymbol)) {
            return false;
        }

        for (String productionKey : productions.keySet()) {
            if (!nonTerminals.contains(productionKey)) {
                return false;
            }

            for (List<String> prods : productions.get(productionKey)) {
                for (String symbol : prods) {
                    if (!nonTerminals.contains(symbol) && !terminals.contains(symbol)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Grammar(String filename) throws IOException {
        this.nonTerminals = new HashSet<>();
        this.terminals = new HashSet<>();
        this.startingSymbol = "";
        this.productions = new HashMap<>();
        this.orderedProductions = new ArrayList<>();

        readFromFile(filename);
    }

    public void readFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList(reader.readLine().split(" ")));
        Set<String> terminals = new HashSet<>(Arrays.asList(reader.readLine().split(" ")));
        String startingSymbol = reader.readLine().trim();

        String line = reader.readLine();
        Map<String, List<List<String>>> productions = new HashMap<>();

        List<Pair<String, List<String>>> orderedProductions = new ArrayList<>();

        for (String nonTerminal : nonTerminals) {
            productions.put(nonTerminal, new ArrayList<>());
        }

        while (line != null) {
            StringTokenizer st = new StringTokenizer(line, "|~");
            String currentSymbol = st.nextToken().trim();
            while (st.hasMoreTokens()) {
                StringTokenizer tokenizer = new StringTokenizer(st.nextToken(), " ");

                List<String> symbols = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    symbols.add(tokenizer.nextToken());
                }
                productions.get(currentSymbol).add(symbols);

                orderedProductions.add(new Pair<>(currentSymbol, symbols));
            }
            line = reader.readLine();
        }
        reader.close();

        if (!validateGrammar(nonTerminals, terminals, startingSymbol, productions)) {
            throw new RuntimeException("Invalid grammar, please check input file.\n");
        }

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.startingSymbol = startingSymbol;
        this.productions = productions;
        this.orderedProductions = orderedProductions;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public Map<String, List<List<String>>> getProductions() {
        return productions;
    }

    public List<List<String>> getProductionsForNonTerminal(String nonTerminal) {
        return productions.get(nonTerminal);
    }

    public List<Pair<String, List<String>>> getOrderedProductions() {
        return orderedProductions;
    }

    public List<String> getAllSymbols() {
        return Stream.concat(nonTerminals.stream(), terminals.stream()).collect(Collectors.toList());
    }
}
