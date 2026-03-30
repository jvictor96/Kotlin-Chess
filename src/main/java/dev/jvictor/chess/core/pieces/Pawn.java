package dev.jvictor.chess.core.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import dev.jvictor.chess.core.Piece;
import dev.jvictor.chess.core.Position;

public class Pawn extends Piece {
    public Pawn(Position position) {
        this.position = position;
    }

    public boolean isMovementValid(Position destination, Piece pieceThere) {
        return Map.of(Color.BLACK,
            Stream.of(
                !isMoved && destination.y == position.y - 2 && pieceThere == null,
                destination.y == position.y - 1 && pieceThere == null,
                destination.y == position.y - 1 && Math.abs(destination.x - position.x) == 1 && 
                pieceThere != null && pieceThere.color != color
            ).anyMatch(Boolean::booleanValue),
            Color.WHITE,
            Stream.of(
                !isMoved && destination.y == position.y + 2 && pieceThere == null, 
                destination.y == position.y + 1 && pieceThere == null,
                destination.y == position.y + 1 && Math.abs(destination.x - position.x) == 1 && 
                pieceThere != null && pieceThere.color != color
            ).anyMatch(Boolean::booleanValue)
        ).get(color);
    }
    public List<Position> getMiddlePlaces(Position destination) {
        if (!isMovementValid(destination, null)) return new ArrayList<>();
        List<Position> ans = Map.of(
            Color.WHITE == color && destination.y - position.y == 2 ? 0 : 1,
            List.of(position.add(0, 1)),
            Color.BLACK == color && destination.y - position.y == - 2 ? 0 : 2,
            List.of(position.add(0, -1))
        ).get(0);
        return ans != null ? ans : new ArrayList<>();
    }
    public List<Position> getAllPossibleDestinations() {
        return Map.of(
            Color.WHITE,
            List.of(position.add(0, 1), position.add(0, 2), position.add(1, 1), position.add(-1, 1)),
            Color.BLACK,
            List.of(position.add(0, -1), position.add(0, -2), position.add(1, -1), position.add(-1, -1))
        ).get(color);
    }
    public boolean isValidRoque(Position destination, Map<Position, Piece> pieces) {
        return false;
    }
    public String getSymbol(){
        return "P";
    }
}
