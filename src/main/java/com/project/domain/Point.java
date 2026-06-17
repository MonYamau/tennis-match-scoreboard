package com.project.domain;

public enum Point {
    ZERO,
    FIFTEEN,
    THIRTY,
    FORTY,
    ADVANTAGE,
    GAME;

    public Point next() {
        switch (this) {
            case ZERO -> {
                return FIFTEEN;
            }
            case FIFTEEN -> {
                return THIRTY;
            }
            case THIRTY -> {
                return FORTY;
            }
            case FORTY -> {
                return ADVANTAGE;
            }
            case ADVANTAGE -> {
                return GAME;
            }
            default -> throw new IllegalArgumentException("Failed to identify the game counter");
        }
    }
}