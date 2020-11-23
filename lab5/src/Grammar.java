import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {
    Set<String> nonTerminals;
    Set<String> terminals;
    String startingSymbol;
    Map<String, List<List<String>>> productions;

    public Grammar() {
        this.nonTerminals = new HashSet<>();
        this.terminals = new HashSet<>();
        this.startingSymbol = "";
        this.productions = new HashMap<>();

        readFromFile("g1.txt");
    }

    public void readFromFile(String filename) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            this.nonTerminals = new HashSet<>(Arrays.asList(reader.readLine().split(" ")));
            this.terminals = new HashSet<>(Arrays.asList(reader.readLine().split(" ")));
            this.startingSymbol = reader.readLine().trim();

            String line = reader.readLine();
            this.productions = new HashMap<>();


            for(String nonTerminal : this.nonTerminals) {
                this.productions.put(nonTerminal, new ArrayList<>());
            }

            while (line != null) {
                StringTokenizer st = new StringTokenizer(line, "|->");
                String startingSymbol = st.nextToken().trim();
                while(st.hasMoreTokens()) {
                    StringTokenizer tokenizer = new StringTokenizer(st.nextToken(), " ");

                    List<String> symbols = new ArrayList<>();
                    while(tokenizer.hasMoreTokens()) {
                        symbols.add(tokenizer.nextToken());
                    }
                    this.productions.get(startingSymbol).add(symbols);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
}
