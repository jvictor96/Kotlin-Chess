package dev.jvictor.chess.ports

import dev.jvictor.chess.core.Board
import java.util.*

interface PersistenceAdapter {
    fun getBoard(id: UUID): Board?
    fun saveBoard(id: UUID, board: Board)
    fun clearAll()
    fun listGames(): MutableList<Board>
}
