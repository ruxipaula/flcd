package com.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public class Controller {
    private FiniteAutomata fa;

    public Controller(FiniteAutomata fa, String filename) {
        this.fa = fa;
        readFromFile(filename);
    }

    public List<String> getStates() {
        return fa.getStates();
    }

    public List<String> getAlphabet() {
        return fa.getAlphabet();
    }

    public List<String> getFinalStates() {
        return fa.getFinalStates();
    }

//    public List<Transition> getTransitions() {
//        return fa.getTransitions();
//    }

    public Map<Pair, List<String>> getTransitions() {
        return fa.getTransitions();
    }

    public String getInitialState() {
        return fa.getInitialState();
    }

    public void readFromFile(String filename) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                processLine(line);
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if(!validate()) {
            throw new AssertionError("Invalid fa.");
        }
    }

    public void processLine(String line) throws AssertionError {
        StringTokenizer st = new StringTokenizer(line, " ={,}");
        String type = st.nextToken();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            switch (type) {
                case "Q":
                    fa.addState(token);
                    break;
                case "E":
                    fa.addToAlphabet(token);
                    break;
                case "F":
                    fa.addFinalState(token);
                    break;
                case "q0":
                    fa.setInitialState(token);
                    break;
                case "S":
                    createTransition(token);
                    break;
                default:
                    //todo: throw exception
            }
        }
    }

    public void createTransition(String transitionToken) throws  IllegalArgumentException {
        StringTokenizer st = new StringTokenizer(transitionToken, "();->");
        String start = st.nextToken();
        String symbol = st.nextToken();
        String end = st.nextToken();

        fa.addTransition(new Pair(start, symbol), end);
    }

    public boolean checkDFA() {
        for (Pair pair : fa.getTransitions().keySet()) {
            if(fa.getTransitions().get(pair).size() > 1) {
                return false;
            }
        }

        return true;
    }

    public boolean validateTransition(String start, String symbol, List<String> end) {
        if (!fa.getStates().contains(start) || !fa.getAlphabet().contains(symbol)) {
            return false;
        }
        for(String s : end) {
            if(!fa.getStates().contains(s)) {
                return false;
            }
        }
        return true;
    }

    public boolean validate() {
        if (!fa.getStates().contains(fa.getInitialState())) {
            return false;
        }
        for (String finalState : fa.getFinalStates()) {
            if(!fa.getStates().contains(finalState)) {
                return false;
            }
        }
        for(Pair pair : fa.getTransitions().keySet()) {
            if(!validateTransition(pair.getX(), pair.getY(), fa.getTransitions().get(pair))) {
                return false;
            }
        }
        return true;
    }

    public boolean accept(String sequence) {
        if (checkDFA()) {
            String[] states = sequence.split("(?!^)");

            String currentState = fa.getInitialState();
            for (String state : states) {
                Pair pair = new Pair(currentState, state);
                if (fa.getTransitions().containsKey(pair)) {
                    currentState = fa.getTransitions().get(pair).get(0);
                } else {
                    return false;
                }
            }
            return fa.getFinalStates().contains(currentState);
        }
        return false;
    }
}
