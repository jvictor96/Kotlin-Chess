package dev.jvictor.chess.core;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import dev.jvictor.chess.core.Piece.Color;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    List<Piece> pieces;
    public List<String> movements;
    Map<Position, Piece> positions;
    public String white, black, winner;
    public boolean legal;
    public int id;

    public Board() {
        pieces = new ArrayList<>();
        movements = new ArrayList<>();
        IntStream.range(1,9).forEach(i -> pieces.add(Piece.fromType("P", Color.WHITE, new Position(i, 2))));
        IntStream.range(1,9).forEach(i -> pieces.add(Piece.fromType("P", Color.BLACK, new Position(i, 7))));
        pieces.add(Piece.fromType("R", Color.WHITE, new Position(1, 1)));
        pieces.add(Piece.fromType("N", Color.WHITE, new Position(2, 1)));
        pieces.add(Piece.fromType("B", Color.WHITE, new Position(3, 1)));
        pieces.add(Piece.fromType("Q", Color.WHITE, new Position(4, 1)));
        pieces.add(Piece.fromType("K", Color.WHITE, new Position(5, 1)));
        pieces.add(Piece.fromType("B", Color.WHITE, new Position(6, 1)));
        pieces.add(Piece.fromType("N", Color.WHITE, new Position(7, 1)));
        pieces.add(Piece.fromType("R", Color.WHITE, new Position(8, 1)));
        pieces.add(Piece.fromType("R", Color.BLACK, new Position(1, 8)));
        pieces.add(Piece.fromType("N", Color.BLACK, new Position(2, 8)));
        pieces.add(Piece.fromType("B", Color.BLACK, new Position(3, 8)));
        pieces.add(Piece.fromType("Q", Color.BLACK, new Position(4, 8)));
        pieces.add(Piece.fromType("K", Color.BLACK, new Position(5, 8)));
        pieces.add(Piece.fromType("B", Color.BLACK, new Position(6, 8)));
        pieces.add(Piece.fromType("N", Color.BLACK, new Position(7, 8)));
        pieces.add(Piece.fromType("R", Color.BLACK, new Position(8, 8)));
        positions = pieces.stream().collect(Collectors.toMap(Piece::getPosition, Function.identity()));
    }

    Board(List<Piece> pieces, List<String> movements) {
        this.pieces = pieces;
        this.movements = movements;
        positions = pieces.stream().collect(Collectors.toMap(Piece::getPosition, Function.identity()));
    }

    public Board clone() {
        List<Piece> clonedPieces = new ArrayList<>();
        clonedPieces.addAll(pieces.stream().map(Piece::clone).toList());
        List<String> clonedMovements = new ArrayList<>();
        clonedMovements.addAll(movements);
        return new Board(clonedPieces, clonedMovements);
    }

    public Piece getPieceAt(String position) {
        return positions.get(Position.fromString(position));
    }

    public boolean isColorInCheck(Piece.Color color) {
        Piece king = pieces.stream().filter(p -> p.getSymbol().equals("K") && p.color == color).findFirst().orElseThrow();
        return pieces.stream().filter(p -> p.color != color).map(
            p -> new Movement(p.position, king.position, positions).isMovementValid()).anyMatch(
                Boolean::booleanValue
            );
    }

    private record PieceAndDestinations(Piece piece, List<Position> destinations) {}

    public boolean isColorInCheckMate(Piece.Color color) {
        Predicate<List<Position>> cloneAndMove = (List<Position> positions) -> {
            Board clone = this.clone();
            Movement movement = new Movement(positions.get(0), positions.get(0), clone.positions);
            return clone.move(movement).legal;
        };
        Stream<PieceAndDestinations> tuples = pieces.stream().map(
            p -> new PieceAndDestinations(p, p.getAllPossibleDestinations()));

        Stream<PieceAndDestinations> valid = tuples.filter(pAndD -> pAndD.destinations.stream().anyMatch(
                d -> cloneAndMove.test(List.of(pAndD.piece.position, d))));
                
        return valid.peek(pAndD -> System.out.println(pAndD.piece)).toList().size() == 0;
    }

    private void updatePositions(Movement movement) {
        noHistoryChangeUpdatePositions(movement);
        if (movement.rookRoque != null) noHistoryChangeUpdatePositions(movement.rookRoque);
        movements.add(movement.toString());
        positions.get(movement.to).isMoved = true;
    }

    private void noHistoryChangeUpdatePositions(Movement movement) {
        movement.piece.position = movement.to;
        positions.put(movement.to, positions.get(movement.from));
        positions.remove(movement.from);
        pieces = positions.values().stream().toList();
    }

    public Board moveWithoutValidation(Movement movement) {
        noHistoryChangeUpdatePositions(movement);
        return this;
    }

    public Movement buildMovement(String movement){
        return Movement.fromString(movement, positions);
    }

    public Board move(Movement movement) {
        legal = movement.isMovementValid();
        if (!legal) return this;
        boolean rightTurn = movements.size() % 2 == 0 && movement.piece.color == Color.WHITE;
        rightTurn = rightTurn || movements.size() % 2 == 1 && movement.piece.color == Color.BLACK;
        legal = rightTurn;
        if (!legal) return this;
        Board board = this.clone();
        board.updatePositions(board.buildMovement(movement.toString()));
        legal = !board.isColorInCheck(movement.piece.color);
        if (!legal) return this;
        if (
            board.isColorInCheck(movement.piece.color == Color.WHITE ? Color.BLACK : Color.WHITE) &&
            board.isColorInCheckMate(movement.piece.color == Color.WHITE ? Color.BLACK : Color.WHITE)
        ) winner = movement.piece.color == Color.WHITE ? white : black;
        updatePositions(movement);
        return this;
    }

}
