package com.flcdproject;

import java.util.*;

public class SymbolTable {
    private final int size = 256;
    private List<List<String>> elems;

    public SymbolTable() {
        this.elems = new ArrayList<>();

        for (int i = 0; i< this.size; i++) {
            this.elems.add(new ArrayList<>());
        }
    }

//    public void add(String token) {
//        elems.get(hashCode(token)).add(token);
//    }

    public Map.Entry<Integer, Integer> addAndReturnPosition(String token) {
        List<String> values = elems.get(hashCode(token));
        if (!values.contains(token)) {
            values.add(token);
        }
        return new AbstractMap.SimpleEntry<>(hashCode(token), values.indexOf(token));
    }

    public boolean search(String token) {
        return elems.get(hashCode(token)).contains(token);
    }

    private int hashCode(String token) {
        long sum = 0;
        for (int index = 0; index < token.length(); index++) {
            sum += token.charAt(index);
        }
        return (int) (sum % size);
    }

    public List<List<String>> getElems() {
        return elems;
    }
}
