package com.lab2;

import java.util.*;

public class FiniteAutomata {
    private List<String> states;
//    private List<Transition> transitions;
    private Map<Pair, List<String>> trans;
    private List<String> finalStates;
    private List<String> alphabet;
    private String initialState;


    public FiniteAutomata() {
        this.states = new ArrayList<>();
//        this.transitions = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.trans = new HashMap<>();
    }

    public void addState(String state) {
        states.add(state);
    }

//    public void addTransition(Transition transition) {
//        transitions.add(transition);
//    }

    public void addTransition(Pair pair, String state) {
        if (trans.containsKey(pair)) {
            trans.get(pair).add(state);
        } else {
            trans.put(pair, new ArrayList<>());
            trans.get(pair).add(state);
        }
    }

    public void addFinalState(String finalState) {
        finalStates.add(finalState);
    }

    public void addToAlphabet(String elem) {
        alphabet.add(elem);
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public List<String> getStates() {
        return states;
    }

//    public List<Transition> getTransitions() {
//        return transitions;
//    }

    public Map<Pair, List<String>> getTransitions() {
        return trans;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public String getInitialState() {
        return initialState;
    }
}
