package com.lab2;

import java.util.ArrayList;
import java.util.List;

public class FiniteAutomata {
    private List<String> states;
    private List<Transition> transitions;
    private List<String> finalStates;
    private List<String> alphabet;
    private String initialState;


    public FiniteAutomata() {
        this.states = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.alphabet = new ArrayList<>();
    }

    public void addState(String state) {
        states.add(state);
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
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

    public List<Transition> getTransitions() {
        return transitions;
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
