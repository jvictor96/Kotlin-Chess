package dev.jvictor.chess

import dev.jvictor.chess.ports.PersistenceAdapter
import dev.jvictor.chess.core.Board
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MatchService(private val persistence: PersistenceAdapter) {
    fun getPlayerMatches(player: String): List<Board> {
        return persistence.findAll().filter{ it.white == player}
    }

    fun challengePlayer(player: String, opponent: String, movement: String): Board {
        val board = Board()
        board.white = player
        board.black = opponent
        board.move(board.buildMovement(movement))
        persistence.save(board)
        return board
    }

    fun movePiece(boardId: UUID, player: String, movement: String): Board? {
        val board = persistence.getBoardById(boardId) ?: return null
        board.build();
        board.move(board.buildMovement(movement))
        persistence.save(board)
        return board
    }

    fun resign(boardId: UUID, player: String): Board? {
        val board = persistence.getBoardById(boardId) ?: return null
        board.winner = if (board.white == player) board.black else board.white
        persistence.save(board)
        return board
    }

    fun getMatch(board: UUID, player: String): Board? {
        val board = persistence.getBoardById(board) ?: return null
        return board
    }
}