package com.project.domain;

public enum Point {
    ZERO,
    FIFTEEN,
    THIRTY,
    FORTY,
    ADVANTAGE;

    public Point next() {
        if (this.equals(ZERO)) {
            return FIFTEEN;
        }
        else if (this.equals(FIFTEEN)) {
            return THIRTY;
        }
        else if (this.equals(THIRTY)) {
            return FORTY;
        }
        return ADVANTAGE;
    }
}