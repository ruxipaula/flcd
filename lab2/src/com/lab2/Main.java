package com.lab2;

public class Main {
    public static void main(String[] args) {
        String filename = "src/data/fa.txt";
        FiniteAutomata fa = new FiniteAutomata();
        Controller controller = new Controller(fa, filename);
        UI userInterface = new UI(controller);
        userInterface.run();
    }
}
