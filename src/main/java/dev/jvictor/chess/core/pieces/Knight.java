package dev.jvictor.chess.core.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.jvictor.chess.core.Piece;
import dev.jvictor.chess.core.Position;

public class Knight extends Piece {
        public Knight(Position position) {
            this.position = position;
        }
        public boolean isMovementValid(Position destination, Piece pieceThere) {
            return (Math.abs(destination.x-position.x) == 2 && Math.abs(destination.y-position.y) == 1)
                || (Math.abs(destination.x-position.x) == 1 && Math.abs(destination.y-position.y) == 2);
        }
        public List<Position> getMiddlePlaces(Position destination) {
            return new ArrayList<>();
        }
        public List<Position> getAllPossibleDestinations() {
            return List.of(
                position.add(1, 2), position.add(2, 1),
                position.add(-1, 2), position.add(-2, 1),
                position.add(1, -2), position.add(2, -1),
                position.add(-1, -2), position.add(-2, -1)
            );
        }
        public boolean isValidRoque(Position destination, Map<Position, Piece> pieces) {
            return false;
        }
        public String getSymbol(){
            return "N";
        }
    }
