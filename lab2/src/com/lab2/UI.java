package com.lab2;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    private Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
    }

    private static void showMenu() {
        System.out.println("1 - States");
        System.out.println("2 - Alphabet");
        System.out.println("3 - Transitions");
        System.out.println("4 - Final states");
        System.out.println("5 - Initial state");
        System.out.println("6 - DFA check");
    }

    public void displayStates() {
        List<String> states = controller.getStates();
        StringBuilder sb = new StringBuilder("Q = {");
        for (String state : states) {
            sb.append(state).append(" ");
        }
        sb.append("}");
        System.out.println(sb);
    }

    public void displayAlphabet() {
        List<String> states = controller.getAlphabet();
        StringBuilder sb = new StringBuilder("E = {");
        for (String state : states) {
            sb.append(state).append(" ");
        }
        sb.append("}");
        System.out.println(sb);
    }

    public void displayFinalStates() {
        List<String> states = controller.getFinalStates();
        StringBuilder sb = new StringBuilder("F = {");
        for (String state : states) {
            sb.append(state).append(" ");
        }
        sb.append("}");
        System.out.println(sb);
    }

    public void displayInitialState() {
        String initialState = controller.getInitialState();
        System.out.println("q0 = " + initialState);
    }

    public void displayTransitions() {
        Map<Pair, List<String>> transitions = controller.getTransitions();
        System.out.println("S = {");
        for (Pair pair: transitions.keySet()) {
            String sb = "(" +
                    pair.getX() +
                    ", " +
                    pair.getY() +
                    ") => " +
                    transitions.get(pair);
            System.out.println(sb);
        }
        System.out.println("}");
    }

    public void checkDFA() {
        boolean isDFA = controller.checkDFA();
        if (isDFA) {
            System.out.println("Check DFA - passed");
        }
        else {
            System.out.println("Check DFA - failed");
        }
    }

    public void acceptSequence() {
        Scanner scanner = new Scanner(System.in);
        String seq = scanner.nextLine();
        if(controller.accept(seq)) {
            System.out.println("Sequence accepted");
        } else {
            System.out.println("Sequence is NOT accepted");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            UI.showMenu();
            System.out.println(">> ");

            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        displayStates();
                        break;
                    case 2:
                        displayAlphabet();
                        break;
                    case 3:
                        displayTransitions();
                        break;
                    case 4:
                        displayFinalStates();
                        break;
                    case 5:
                        displayInitialState();
                        break;
                    case 6:
                        checkDFA();
                        break;
                    case 7:
                        acceptSequence();
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
}
