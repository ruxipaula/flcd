package com.flcdproject;

public class PifPair {
    private String token;
    private int x;
    private int y;

    public PifPair(String token, int x, int y) {
        this.token = token;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getToken() {
        return token;
    }
}
