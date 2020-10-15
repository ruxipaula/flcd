package com.flcdproject;

public class Main {
    public static void main(String[] args) {
        SymbolTable st = new SymbolTable();
        st.add("b");
        st.add("a");
        st.add("c");

        System.out.println(st.search("a"));
        System.out.println(st.position("a"));
        System.out.println(st.position("b"));
        System.out.println(st.position("c"));
    }
}
