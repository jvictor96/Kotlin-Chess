package dev.jvictor.chess.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import dev.jvictor.chess.core.pieces.Bishop;
import dev.jvictor.chess.core.pieces.King;
import dev.jvictor.chess.core.pieces.Knight;
import dev.jvictor.chess.core.pieces.Pawn;
import dev.jvictor.chess.core.pieces.Queen;
import dev.jvictor.chess.core.pieces.Rook;

public abstract class Piece {

    public enum Color {
        WHITE, BLACK
    }

    public Position position;
    public Color color;
    public boolean isMoved = false;

    public abstract boolean isMovementValid(Position destination, Piece pieceThere);
    public abstract List<Position> getMiddlePlaces(Position destination);
    public abstract List<Position> getAllPossibleDestinations();
    public abstract boolean isValidRoque(Position destination, Map<Position, Piece> pieces);
    public abstract String getSymbol();

    public static Piece fromType(String type, Color color, Position position) {
        Map<String, Function<Position, Piece>> typeToPiece = new HashMap<String, Function<Position, Piece>>();
        typeToPiece.put("P", x -> new Pawn(x));
        typeToPiece.put("R", x -> new Rook(x));
        typeToPiece.put("N", x -> new Knight(x));
        typeToPiece.put("B", x -> new Bishop(x));
        typeToPiece.put("Q", x -> new Queen(x));
        typeToPiece.put("K", x -> new King(x));
        Piece piece = typeToPiece.get(type).apply(position);
        piece.color = color;
        return piece;
    }
    
    public Position getPosition() {
        return position;
    }

    public Piece clone() {
        return Piece.fromType(getSymbol(), color, Position.fromString(position.toString()));
    }
}
