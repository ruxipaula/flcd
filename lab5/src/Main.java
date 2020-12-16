import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Grammar grammar;

    public static void main(String[] args) {
        try {
            grammar = new Grammar("grammar.txt");
            run();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static void showMenu() {
        System.out.println("1 - Non-terminals");
        System.out.println("2 - Terminals");
        System.out.println("3 - Starting Symbol");
        System.out.println("4 - Productions");
        System.out.println("5 - Production for a given non-terminal");
        System.out.println("6 - Run LR(0) parser");
    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            showMenu();
            System.out.println(">> ");

            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        displayNonTerminals();
                        break;
                    case 2:
                        displayTerminals();
                        break;
                    case 3:
                        displayStartingSymbol();
                        break;
                    case 4:
                        displayProductions();
                        break;
                    case 5:
                        displayProductionsForNonTerminal();
                        break;
                    case 6:
                        runLR0Parser();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        throw new AssertionError("\nError - Unknown operation \n");
                }
            } catch (InputMismatchException | AssertionError | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayNonTerminals() {
        for (String nonTerminal : grammar.getNonTerminals()) {
            System.out.print(nonTerminal + " ");
        }
        System.out.println();
    }

    private static void displayTerminals() {
        for (String terminal : grammar.getTerminals()) {
            System.out.print(terminal + " ");
        }
        System.out.println();
    }

    private static void displayStartingSymbol() {
        System.out.println(grammar.getStartingSymbol());
    }

    private static void displayProductions() {
        Map<String, List<List<String>>> productions = grammar.getProductions();
        for (String nonTerminal : productions.keySet()) {
            System.out.print(nonTerminal + " -> ");
            List<List<String>> nonTerminalProductions = productions.get(nonTerminal);
            for (List<String> production : nonTerminalProductions) {
                for (String value : production) {
                    System.out.print(value);
                }

                if (production != nonTerminalProductions.get(nonTerminalProductions.size() - 1)) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void displayProductionsForNonTerminal() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("NonTerminal: ");
        String nonTerminal = scanner.nextLine();

        List<List<String>> productions = grammar.getProductionsForNonTerminal(nonTerminal);

        System.out.print(nonTerminal + " -> ");
        for (List<String> production : productions) {
            for (String value : production) {
                System.out.print(value);
            }

            if (production != productions.get(productions.size() - 1)) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    private static void runLR0Parser() throws IOException {
        Parser parser = new Parser(grammar);
        CanonicalCollection collection = parser.canonicalCollection();

        // Print the Canonical collection.
        System.out.println("\nCanonical collection: ");
        for (List<LRItem> state : collection.getCollection()) {
            System.out.println("s" + collection.getCollection().indexOf(state) + " = " + state);
        }

        // Print the LR(0) table.
        System.out.println("\nLR(0) table: ");
        LR0Table lr0Table = new LR0Table(collection, grammar.getOrderedProductions(), grammar.getAllSymbols());
//        System.out.println(lr0Table.toString());

        // Parse a given sequence.
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Give a sequence:\nw = ");
//        List<String> sequence = Arrays.asList(scanner.nextLine().split("[ ]+"));
        List<String> sequence = readSequence("sequence.txt");

        Stack<Integer> output = parser.parseSequence(grammar, lr0Table, sequence);

        while (!output.empty()) {
            System.out.print(output.pop() + ", ");
        }

        output = parser.parseSequence(grammar, lr0Table, sequence);
        ParserOutput parserOutput = new ParserOutput();
        parserOutput.addParsedSequence(output, grammar);

        System.out.println("\nLR(0) parsing tree:");
        parserOutput.traverseTree(parserOutput.getRoot());

        System.out.println("\n");
    }

    public static List<String> readSequence(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> sequence = new ArrayList<>();
        String line = reader.readLine();

        while(line != null) {
            sequence.addAll(Arrays.asList(line.split("[ ]+")));
            line = reader.readLine();
        }
        return sequence;
    }
}




