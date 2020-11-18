import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Grammar grammar;

    public static void main(String[] args) {
        grammar = new Grammar();
        run();
    }

    private static void showMenu() {
        System.out.println("1 - Non-terminals");
        System.out.println("2 - Terminals");
        System.out.println("3 - Starting Symbol");
        System.out.println("4 - Productions");
        System.out.println("5 - Production for a given non-terminal");
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
                    case 0:
                        running = false;
                        break;
                    default:
                        throw new AssertionError("\nError - Unknown operation \n");
                }
            } catch (InputMismatchException | AssertionError e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void displayNonTerminals() {
        for(String nonTerminal : grammar.getNonTerminals()) {
            System.out.print(nonTerminal + " ");
        }
        System.out.println();
    }

    private static void displayTerminals() {
        for(String terminal : grammar.getTerminals()) {
            System.out.print(terminal + " ");
        }
        System.out.println();
    }

    private static void displayStartingSymbol() {
        System.out.println(grammar.getStartingSymbol());
    }

    private static void displayProductions() {
        Map<String, List<String>> productions = grammar.getProductions();
        for(String nonTerminal : productions.keySet()) {
            for(String value: productions.get(nonTerminal)) {
                System.out.println(nonTerminal + " -> " + value);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void displayProductionsForNonTerminal() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("NonTerminal: ");
        String nonTerminal = scanner.nextLine();

        List<String> productions = grammar.getProductionsForNonTerminal(nonTerminal);
        for(String value: productions) {
            System.out.println(nonTerminal + " -> " + value);
        }
        System.out.println();
    }
}




