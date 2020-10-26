package com.flcdproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    private SymbolTable st;
    private Pif pif;

    public Scanner() {
        this.st = new SymbolTable();
        this.pif = new Pif();
    }

    private List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private List<String> tokenize(String line) {
//        List<String> tokens = Arrays.asList("int", "string", "const", "do", "else", "if", "program", "readInt", "readString",
//                "while", "print", "[", "]", "{", "}", ";", " ", "+", "-", "*", "/", "%", "=", "<", ">", "<=", ">=", "==", "!=", "&&", "||");

        List<String> tokens = new ArrayList<>();
        String separatorString = "int string const do else if program readInt readString while print return [ ] { } ; space + - * / % = > < <= >= == != & |";
        StringTokenizer st = new StringTokenizer(line, " ()[]{};+-*/%=><!&|", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!token.equals(" ")) {
                if(token.equals("<") || token.equals(">")) {
                    String nextToken = st.nextToken();
                    if (nextToken.equals("=")) {
                        tokens.add(token+nextToken);
                    }
                    else {
                        tokens.add(token);
                        tokens.add(nextToken);
                    }
                }
                else {
                    if(token.equals("&") || token.equals("|")) {
                        String nextToken = st.nextToken();
                        if (token.equals(nextToken)) {
                            tokens.add(token+nextToken);
                        }
                        else {
                            tokens.add(token);
                            tokens.add(nextToken);
                        }
                    }
                    else {
                        tokens.add(token);
                    }
                }
            }
        }

        return tokens;
    }

    private boolean isIdentifier(String token) {
        String pattern = "^[a-zA-Z]([a-zA-Z]|[0-9]|_)*$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(token);

        return m.find();
    }

    private boolean isConstant(String token) {
        String pattern = "^[1-9]+[0-9]*$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(token);

        return m.find();
    }

    public void start() {
        String filename = "src/data/a.txt";
        List<String> lines = readFile(filename);

        List<String> all = Arrays.asList("int", "string", "const", "do", "else", "if", "program", "readInt", "readString",
                "while", "print", "(", ")", "[", "]", "{", "}", ";", " ", "+", "-", "*", "/", "%", "=", "<", ">", "<=", ">=", "==", "!=", "&&", "||");

        for (String line : lines) {
            List<String> tokens = tokenize(line);
            for (String token : tokens) {
                if (all.contains(token)) {
                    pif.add(token, new Pair(-1, -1));
                } else {
                    if (isIdentifier(token)) {
                        // put 1 in pif
                        Map.Entry<Integer, Integer> pos = st.addAndReturnPosition(token);
                        pif.add("1", new Pair(pos.getKey(), pos.getValue()));
                    } else {
                        if (isConstant(token)) {
                            // put 0 in pif
                            Map.Entry<Integer, Integer> pos = st.addAndReturnPosition(token);
                            pif.add("0", new Pair(pos.getKey(), pos.getValue()));
                        }
                        else {
                            throw new AssertionError("Unable to classify token");
                        }
                    }

                }
            }

        }

        System.out.println("Classification done");


    }
}
