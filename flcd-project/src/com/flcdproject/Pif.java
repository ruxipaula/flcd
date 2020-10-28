package com.flcdproject;

import java.util.ArrayList;
import java.util.List;

public class Pif {
    private List<PifPair> pif;

    public Pif() {
        this.pif = new ArrayList<>();
    }

    public void add(String string, Pair code) {
        pif.add(new PifPair(string, code.getX(), code.getY()));
    }

    public List<PifPair> getTable() {
        return pif;
    }
}
