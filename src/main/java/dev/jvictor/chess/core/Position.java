package dev.jvictor.chess.core;

import java.util.Objects;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position fromString(String position) {
        int x = position.charAt(0) - 96; 
        int y = Integer.parseInt(position.substring(1,2)); 
        return new Position(x, y);
    }

    public boolean isValid() {
        return x >= 1 && x <= 8 && y>=1 && y<=8; 
    }

    @Override
    public String toString() {
        return "%c%d".formatted((char) 96+x, y);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object other) {
        return other.hashCode() == this.hashCode();
    }

    public Position add(int i, int j) {
        return new Position(x + i, y + j);
    }
}
