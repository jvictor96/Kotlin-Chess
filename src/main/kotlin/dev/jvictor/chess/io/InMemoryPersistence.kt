package dev.jvictor.chess.io

import dev.jvictor.chess.core.Board
import dev.jvictor.chess.ports.PersistenceAdapter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.ArrayList
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

@Repository
@Profile("dev", "local")
class InMemoryPersistence : PersistenceAdapter {
    var boards: MutableList<Board> = CopyOnWriteArrayList<Board>()
    var nextId: Int = 0

    override fun getBoardById(id: UUID): Board? {
        return boards.firstOrNull() { it.id == id }
    }

    override fun save(board: Board): Board {
        boards.add(board)
        return board
    }

    override fun findAll(): MutableList<Board> {
        return boards
    }
}
