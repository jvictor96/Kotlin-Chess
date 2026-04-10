package dev.jvictor.chess.ports

import dev.jvictor.chess.core.Board
import java.util.*

interface PersistenceAdapter {
    fun getBoardById(id: UUID): Board?
    fun save(board: Board): Board
    fun findAll(): MutableList<Board>
}
