package com.flcdproject;

import java.util.HashMap;
import java.util.Map;

public class Pif {
    private int pos;
    private Map<String, Pair> table;

    public Pif() {
        this.table = new HashMap<>();
        this.pos = 1;
    }

    public void add(String string, Pair code) {
        table.put(string, code);
        pos++;
    }
}
