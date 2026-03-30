package dev.jvictor.chess.core.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import dev.jvictor.chess.core.Piece;
import dev.jvictor.chess.core.Position;

public class Bishop extends Piece {
        public Bishop(Position position) {
            this.position = position;
        }
        
        public boolean isMovementValid(Position destination, Piece pieceThere) {
            return Math.abs(destination.x-position.x) == Math.abs(destination.y-position.y);
        }

        public static List<Position> bishopMiddlePlaces(Position origin, Position destination) {
            if(!(Math.abs(destination.x-origin.x) == Math.abs(destination.y-origin.y))) return new ArrayList<Position>();
            return Map.of(
                destination.x > origin.x && destination.y > origin.y ? 0 : 1, 
                IntStream.range(origin.x + 1, destination.x).mapToObj(i -> new Position(i, origin.y + i - origin.x)).toList(),
                destination.x > origin.x && destination.y < origin.y ? 0 : 2, 
                IntStream.range(origin.x + 1, destination.x).mapToObj(i -> new Position(i, origin.y - i + origin.x)).toList(),
                destination.x < origin.x && destination.y < origin.y ? 0 : 3, 
                IntStream.range(destination.x + 1, origin.x).mapToObj(i -> new Position(i, destination.y - i + origin.x)).toList(),
                destination.x < origin.x && destination.y > origin.y ? 0 : 4, 
                IntStream.range(destination.x + 1, origin.x).mapToObj(i -> new Position(i, destination.y + i - origin.x)).toList(),
                destination.x == origin.x && destination.y == origin.y ? 0 : 5, new ArrayList<Position>()
            ).get(0);
        }

        public List<Position> getMiddlePlaces(Position destination) {
            if (!isMovementValid(destination, null)) return new ArrayList<Position>();
            return bishopMiddlePlaces(position, destination);
        }

        public static List<Position> getPossibleDestinationAsBishop(Position position) {
            int upperY = position.y + position.x - 1;
            int lowerY = position.y - position.x + 1;
            List<Position> rising = new ArrayList<>();
            rising.addAll(
                IntStream.range(1, 9).mapToObj(x -> new Position(x, lowerY + x)).toList()
            );
            rising.addAll(
                IntStream.range(1, 9).mapToObj(x -> new Position(x, upperY - x)).toList()
            );
            return rising.stream().filter(Position::isValid).toList();
        }

        public List<Position> getAllPossibleDestinations() {
            return getPossibleDestinationAsBishop(position);
        }

        public boolean isValidRoque(Position destination, Map<Position, Piece> pieces) {
            return false;
        }

        public String getSymbol(){
            return "B";
        }
    }
