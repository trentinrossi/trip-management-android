package com.rodrigorossi.tripsfinalproject.model;

public enum ActivityOpenMode {
    NEW(1), UPDATE(2);
    private final int code;

    private ActivityOpenMode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
