package dev.jvictor.chess.ports

import dev.jvictor.chess.core.Board
import java.util.*

interface PersistenceAdapter {
    fun getBoard(id: Int): Board?
    fun saveBoard(id: Int, board: Board)
    fun clearAll()
    fun nextId(): Int
    fun listGames(): MutableList<Board>
}
