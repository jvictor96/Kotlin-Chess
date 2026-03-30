package dev.jvictor.chess.core.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.jvictor.chess.core.Piece;
import dev.jvictor.chess.core.Position;

public class King extends Piece {
    public King(Position position) {
        this.position = position;
        isMoved = false;
    }
    public boolean isMovementValid(Position destination, Piece pieceThere) {
        return Math.abs(destination.x-position.x) < 2 && Math.abs(destination.y-position.y) < 2;
    }
    public List<Position> getMiddlePlaces(Position destination) {
        return new ArrayList<>();
    }
    public List<Position> getAllPossibleDestinations() {
        return List.of(
            position.add(1,1), position.add(-1, -1),
            position.add(1, -1), position.add(-1, 1),
            position.add(1, 0), position.add(-1, 0),
            position.add(0, 1), position.add(0, -1));
    }
    public boolean isValidRoque(Position destination, Map<Position, Piece> pieces) {
        Position debug = position.add(2, 0);
        if (destination.equals(debug) && !isMoved) {
            if (pieces.get(position.add(3, 0)) == null || pieces.get(position.add(3, 0)).isMoved) return false;
            return List.of(pieces.get(position.add(1, 0)) == null, pieces.get(position.add(2, 0)) == null).stream().allMatch(Boolean::booleanValue);
        }
        if (destination.equals(position.add(-2, 0)) && !isMoved) {
            if (pieces.get(position.add(-4, 0)) == null || pieces.get(position.add(-4, 0)).isMoved) return false;
            return List.of(pieces.get(position.add(-1, 0)) == null, pieces.get(position.add(-2, 0)) == null, pieces.get(position.add(-3, 0)) == null).stream().allMatch(Boolean::booleanValue);
        }
        return false;
    }
    public String getSymbol(){
        return "K";
    }
}
