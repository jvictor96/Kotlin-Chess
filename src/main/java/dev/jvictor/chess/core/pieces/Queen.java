package dev.jvictor.chess.core.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.jvictor.chess.core.Piece;
import dev.jvictor.chess.core.Position;

public class Queen extends Piece {
    public Queen(Position position) {
        this.position = position;
    }

    private String getMovingAs(Position destination) {
        if (position.x == destination.x && position.y == destination.y) return null;
        return Map.of(
            position.x == destination.x || position.y == destination.y ? 0 : 1, "R",
            Math.abs(destination.x-position.x) == Math.abs(destination.y-position.y) ? 0 : 2, "B"
        ).get(0);
    }

    public boolean isMovementValid(Position destination, Piece pieceThere) {
        return getMovingAs(destination) != null;
    }

    public List<Position> getMiddlePlaces(Position destination) {
        if (!isMovementValid(destination, null)) return new ArrayList<>();
        boolean isRook = getMovingAs(destination) == "R";
        return
        Map.of(
            isRook, Rook.rookMiddlePaces(position, destination),
            !isRook, Bishop.bishopMiddlePlaces(position, destination)
        ).get(true);
    }
    public List<Position> getAllPossibleDestinations() {
        List<Position> asBishop = new ArrayList<>();
        asBishop.addAll(Bishop.getPossibleDestinationAsBishop(position));
        asBishop.addAll(Rook.rookPossibleDestinations(position));
        return asBishop;
    }
    public boolean isValidRoque(Position destination, Map<Position, Piece> pieces) {
        return false;
    }
    public String getSymbol(){
        return "Q";
    }
}
