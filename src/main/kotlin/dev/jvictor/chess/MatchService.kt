package dev.jvictor.chess

import dev.jvictor.chess.ports.PersistenceAdapter
import dev.jvictor.chess.core.Board
import org.springframework.stereotype.Service

@Service
class MatchService(private val persistence: PersistenceAdapter) {
    fun getPlayerMatches(player: String): List<Board> {
        return persistence.listGames().filter{ it.white == player}
    }

    fun challengePlayer(player: String, opponent: String, movement: String): Board {
        val board = Board()
        board.white = player
        board.black = opponent
        board.move(board.buildMovement(movement))
        return board
    }

    fun movePiece(board: Int, player: String, movement: String): Board? {
        val board = persistence.getBoard(board) ?: return null
        board.move(board.buildMovement(movement))
        return board
    }

    fun resign(board: Int, player: String): Board? {
        val board = persistence.getBoard(board) ?: return null
        board.winner = if (board.white == player) board.black else board.white
        return board
    }
}