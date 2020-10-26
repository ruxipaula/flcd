package com.flcdproject;

public class Main {
    public static void main(String[] args) {
        SymbolTable st = new SymbolTable();
        st.addAndReturnPosition("b");
        st.addAndReturnPosition("a");
        st.addAndReturnPosition("c");

        System.out.println(st.search("a"));
        System.out.println(st.addAndReturnPosition("a"));
        System.out.println(st.addAndReturnPosition("b"));
        System.out.println(st.addAndReturnPosition("c"));

        Scanner scanner = new Scanner();
        scanner.start();
    }
}
