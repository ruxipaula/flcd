package com.flcdproject;

import java.io.*;
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

    private void writeToFilePif(String filename, Pif pif) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        List<PifPair> table = pif.getTable();
        for (PifPair entry : table) {
            String token = entry.getToken();
            int x = entry.getX();
            int y = entry.getY();
            writer.append("token: ").append(token).append(" code: ").append("(").append(String.valueOf(x)).append(",").append(String.valueOf(y)).append(")");
            writer.append("\n");
        }

        writer.close();
    }

    private void writeToFileSt(String filename, SymbolTable st) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        List<List<String>> table = st.getElems();
        for (int i = 0; i < table.size(); i++) {
            List<String> list = table.get(i);
            if (list.size() != 0) {
                for (int j = 0; j < list.size(); j++) {
                    writer.append("token: ").append(list.get(j)).append(" position: ").append("(").append(String.valueOf(i)).append(",").append(String.valueOf(j)).append(")");
                    writer.append("\n");
                }
            }
        }
        writer.close();
    }

    private List<String> tokenize(String line) {
        List<String> tokens = new ArrayList<>();
        String separatorString = "int string const do else if program readInt readString while print return [ ] { } ; space + - * / % = > < <= >= == != & |";
        StringTokenizer st = new StringTokenizer(line, " ()[]{};+-*/%=><!&|", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!token.equals(" ")) {
                if (token.equals("<") || token.equals(">")) {
                    String nextToken = st.nextToken();
                    if (nextToken.equals("=")) {
                        tokens.add(token + nextToken);
                    } else {
                        tokens.add(token);
                        tokens.add(nextToken);
                    }
                } else {
                    if (token.equals("&") || token.equals("|")) {
                        String nextToken = st.nextToken();
                        if (token.equals(nextToken)) {
                            tokens.add(token + nextToken);
                        } else {
                            tokens.add(token);
                            tokens.add(nextToken);
                        }
                    } else {
//                        if (token.equals("=")) {
//                            tokens.add(token);
//                            String nextToken = st.nextToken();
//                            if (nextToken.equals("+") || nextToken.equals("-")) {
//
//                            }
//                        }
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
//        String pattern = "^[1-9]+[0-9]*$";
        String pattern = "^(0|[+\\-]?[1-9][0-9]*)$|^\".*\"$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(token);

        return m.find();
    }

    public void start() {
        String filein = "src/data/a.txt";
        String fileoutpif = "src/data/pif.txt";
        String fileoutst = "src/data/st.txt";
        List<String> lines = readFile(filein);

        List<String> all = Arrays.asList("int", "string", "const", "do", "else", "if", "program", "readInt", "readString",
                "while", "print", "return", "(", ")", "[", "]", "{", "}", ";", " ", "+", "-", "*", "/", "%", "=", "<", ">", "<=", ">=", "==", "!=", "&&", "||");

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
                        } else {
                            throw new AssertionError("Unable to classify token: " + token);
                        }
                    }

                }
            }

        }

        System.out.println("Classification done");
        try {
            writeToFilePif(fileoutpif, pif);
            writeToFileSt(fileoutst, st);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
