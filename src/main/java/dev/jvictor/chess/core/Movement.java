package dev.jvictor.chess.core;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Movement {
    public Position from, to;
    public Movement rookRoque;
    Map<Position, Piece> positions;
    Piece piece;

    public Movement(Position from, Position to, Map<Position, Piece> positions) {
        this.positions = positions;
        this.from = from;
        this.to = to;
        piece = positions.get(from);
    }

    public static Movement fromString(String movement, Map<Position, Piece> positions) {
        return new Movement(Position.fromString(movement.substring(0, 2)), Position.fromString(movement.substring(2, 4)), positions);
    }

    public static Movement fromString(String movement) {
        return new Movement(Position.fromString(movement.substring(0, 2)), Position.fromString(movement.substring(2, 4)), new HashMap<>());
    }

    public boolean isMovementValid() {
        if (piece == null) return false;
        if (piece.getSymbol().equals("K") && piece.isValidRoque(to, positions)) {
            rookRoque = Map.of(
                from.add(2, 0), new Movement(from.add(3, 0), from.add(1, 0), positions),
                from.add(-2, 0), new Movement(from.add(-4, 0), from.add(-1, 0), positions)
            ).get(to);
            return true;
        }
        return Stream.of(
            isPieceWiseValid(),
            isPathClear(),
            isAnActualMovement(),
            isDestinationFree(),
            isDestinationValid()
        ).allMatch(Boolean::booleanValue);
    }

    private boolean isPieceWiseValid() {
        return piece.isMovementValid(to, positions.get(to));
    }

    private boolean isPathClear() {
        return piece.getMiddlePlaces(to).stream().noneMatch(positions::containsKey);
    }

    private boolean isDestinationFree() {
        return positions.get(to) == null || positions.get(to).color != piece.color;
    }

    private boolean isAnActualMovement() {
        return to != from;
    }

    private boolean isDestinationValid() {
        return to.isValid();
    }

    public String toString() {
        return "%s%s".formatted(from, to);
    }
}
