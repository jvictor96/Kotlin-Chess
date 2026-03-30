package dev.jvictor.chess.core.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import dev.jvictor.chess.core.Piece;
import dev.jvictor.chess.core.Position;

public class Rook extends Piece {
        public Rook(Position position) {
            this.position = position;
            isMoved = false;
        }
        public boolean isMovementValid(Position destination, Piece pieceThere) {
            return destination.x == position.x || destination.y == position.y;
        }

        public static List<Position> rookMiddlePaces(Position origin, Position destination) {
            if (destination.x != origin.x && destination.y != origin.y) return new ArrayList<Position>();
            return Map.of(
                destination.x > origin.x ? 0 : 1, IntStream.range(origin.x + 1, destination.x).mapToObj(i -> new Position(i, origin.y)).toList(),
                destination.x < origin.x ? 0 : 2, IntStream.range(destination.x + 1, origin.x).mapToObj(i -> new Position(i, origin.y)).toList(),
                destination.y > origin.y ? 0 : 3, IntStream.range(origin.y + 1, destination.y).mapToObj(i -> new Position(origin.x, i)).toList(),
                destination.y < origin.y ? 0 : 4, IntStream.range(destination.y + 1, origin.y).mapToObj(i -> new Position(origin.x, i)).toList(),
                destination.x == origin.x && destination.y == origin.y ? 0 : 5, new ArrayList<Position>()
            ).get(0);
        }

        public List<Position> getMiddlePlaces(Position destination) {
            if (!isMovementValid(destination, null)) return new ArrayList<Position>();
            return rookMiddlePaces(position, destination);
        }

        public static List<Position> rookPossibleDestinations(Position origin) {
            List<Position> sameColumn = new ArrayList<>();
            sameColumn.addAll(IntStream.range(1,9).mapToObj(y -> new Position(origin.x, y)).filter(p -> p.y != origin.y).toList());
            sameColumn.addAll(IntStream.range(1,9).mapToObj(x -> new Position(x, origin.y)).filter(p -> p.y != origin.y).toList());
            return sameColumn;
        }

        public List<Position> getAllPossibleDestinations() {
            return rookPossibleDestinations(position);
        }
        public boolean isValidRoque(Position destination, Map<Position, Piece> pieces) {
            return false;
        }
        public String getSymbol(){
            return "R";
        }
    }
