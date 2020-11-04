package com.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public List<Transition> getTransitions() {
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
            e.printStackTrace();
        }
    }

    public void processLine(String line) {
        StringTokenizer st = new StringTokenizer(line, " ={,}");
        String type = st.nextToken();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            switch(type) {
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

    public void createTransition(String transitionToken) {
        StringTokenizer st = new StringTokenizer(transitionToken, "();->");
        String start = st.nextToken();
        String end = st.nextToken();
        String code = st.nextToken();

        fa.addTransition(new Transition(start, code, end));
    }

    public boolean checkDFA() {
        List<Pair> pairs = new ArrayList<>();
        for(Transition t : fa.getTransitions()) {
            Pair pair = new Pair(t.getInitialState(), t.getCode());
            if (pairs.contains(pair)) {
                return false;
            }
            pairs.add(pair);
        }

        return true;
    }
}
